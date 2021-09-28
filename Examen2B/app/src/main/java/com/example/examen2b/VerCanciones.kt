package com.example.examen2b

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.example.examen2b.dto.AutorDTO
import com.example.examen2b.dto.CancionDTO
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class VerCanciones : AppCompatActivity() {
    var adapter: ArrayAdapter<CancionDTO>? = null
    var arrayCanciones =  arrayListOf<CancionDTO>()
    var posicionItemSelecionado = 0
    val CODIGO_RESPUESTA_INTENT_EXPLICITO = 401
    var usuario: AutorDTO?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_canciones)

        usuario= intent.getParcelableExtra<AutorDTO>("autores")
        Log.i("usuario","Usuario: ${usuario!!.nombre}")

        consultarCancion()

        val botonCrearCancion = findViewById<Button>(R.id.btn_irAActividadCrearCancion)
        botonCrearCancion
            .setOnClickListener {
                abrirActividadConParametros(CrearCanciones::class.java,usuario!!)
            }

    }

    fun consultarCancion(){

        val db = Firebase.firestore
        var refUsuario = db
            .collection("canciones")
        refUsuario
            .whereEqualTo("usuario_uid",usuario!!.uid)
            .get()
            .addOnSuccessListener {
                //Log.i("consultas","${it.documents}")
                for (documentos in it){
                    val ubicacionStore: HashMap<String, *> = documentos.data["ubicacion"] as HashMap<String, *>


                    arrayCanciones.add(
                        CancionDTO(
                            documentos.id,
                            documentos.data["usuario_uid"].toString(),

                            LatLng(ubicacionStore["latitude"].toString().toDouble(),ubicacionStore["longitude"].toString().toDouble()),
                            documentos.data["tituloCancion"].toString(),
                            documentos.data["generoCancion"].toString(),
                            documentos.data["duracionCancion"].toString()

                        )
                    )

                    adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayCanciones)
                    val listViewUsuario = findViewById<ListView>(R.id.ltv_canciones)
                    listViewUsuario.adapter = adapter

                    registerForContextMenu(listViewUsuario)

                }

            }
            .addOnFailureListener{}

    }

    fun abrirActividadConParametros(
        clase: Class<*>,
        usuario: AutorDTO,
    ){
        val intentExplicito = Intent(
            this,
            clase
        )
        intentExplicito.putExtra("autores",usuario)
        startActivityForResult(intentExplicito,CODIGO_RESPUESTA_INTENT_EXPLICITO)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_canciones,menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSelecionado = posicion


    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        //editar, y el egundo caso es eiminar.
        var CancionSeleccionada = arrayCanciones[posicionItemSelecionado]

        return when(item?.itemId){

            //Editar
            R.id.editar_Cancion -> {
                Log.i("list-view-cancion","Editar Cancion id: ${CancionSeleccionada}")
                abrirActividadConParametros3(EditarCancion::class.java,CancionSeleccionada,usuario!!)
                return true
            }

            //Eliminar
            R.id.eliminar_cancion -> {
                Log.i("list-view-cancion","Eliminar Cancion id: ${CancionSeleccionada}")

                val db = Firebase.firestore
                var refCancion = db
                    .collection("canciones")

                refCancion.document(CancionSeleccionada.uid.toString())
                    .delete()
                    .addOnSuccessListener {
                        adapter?.remove(adapter!!.getItem(posicionItemSelecionado));
                        adapter?.notifyDataSetChanged()
                        Log.d("list-view", "DocumentSnapshot successfully deleted!") }
                    .addOnFailureListener { e -> Log.w("list-view", "Error deleting document", e) }

                return true }

            R.id.ver_mapa->{
                abrirActividadConParametros2(VerUbicacion::class.java,CancionSeleccionada)
                return true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    fun abrirActividadConParametros2(
        clase: Class<*>,
        cancion: CancionDTO,
    ){
        val intentExplicito = Intent(
            this,
            clase,
        )
        //intentExplicito.putExtra("nombre","Adrian")
        //intentExplicito.putExtra("usuario",usuario)

        intentExplicito.putExtra("canciones",cancion)
        startActivityForResult(intentExplicito,CODIGO_RESPUESTA_INTENT_EXPLICITO)

    }

    fun abrirActividadConParametros3(
        clase: Class<*>,
        cancion: CancionDTO,
        usuario: AutorDTO,
    ){
        val intentExplicito = Intent(
            this,
            clase,
        )
        //intentExplicito.putExtra("nombre","Adrian")
        //intentExplicito.putExtra("usuario",usuario)

        intentExplicito.putExtra("canciones",cancion)
        intentExplicito.putExtra("autores",usuario)
        startActivityForResult(intentExplicito,CODIGO_RESPUESTA_INTENT_EXPLICITO)

    }
}