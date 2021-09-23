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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    var adapter: ArrayAdapter<AutorDTO>? = null
    var arrayUsuarios =  arrayListOf<AutorDTO>()
    var posicionItemSelecionado = 0
    val CODIGO_RESPUESTA_INTENT_EXPLICITO = 401

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //quemarDatos()

        val botonIrActividadcrearUsuarioUsuario = findViewById<Button>(R.id.btn_ir_crear_autor)
        botonIrActividadcrearUsuarioUsuario
            .setOnClickListener {
                abrirActividad(CrearAutores::class.java)
            }

        consultarUsuario()
    }

    fun abrirActividad(clase: Class<*>){
        val intentExplicito = Intent( //Intent es una clase, solamente para que este bien contextualizado.
            this,
            clase
        )
        startActivity(intentExplicito) //Lo heredamos de la clase.
    }

    fun abrirActividadConParametros(
        clase: Class<*>,
        usuario: AutorDTO,
    ){
        val intentExplicito = Intent(
            this,
            clase,
        )
        //intentExplicito.putExtra("nombre","Adrian")
        intentExplicito.putExtra("autores",usuario)
        startActivityForResult(intentExplicito,CODIGO_RESPUESTA_INTENT_EXPLICITO)

    }

    fun consultarUsuario(){

        val db = Firebase.firestore
        var refUsuario = db
            .collection("autores")
        refUsuario
            .get()
            .addOnSuccessListener {
                //Log.i("consultas","${it.documents}")
                for (documentos in it){
                    Log.i("consultas","${documentos.data["nombre"]}")

                    arrayUsuarios.add(
                        AutorDTO(
                            documentos.id,
                            documentos.data["nombre"].toString(),
                            documentos.data["pais"].toString(),
                            documentos.data["edad"].toString(),

                        )
                    )


                    adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayUsuarios)
                    val listViewUsuario = findViewById<ListView>(R.id.ltv_autores)
                    listViewUsuario.adapter = adapter

                    registerForContextMenu(listViewUsuario)
                }

            }
            .addOnFailureListener{}

    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_autores,menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSelecionado = posicion
        //Log.i("list-view","onCreate ${posicionItemSelecionado}")
        //Log.i("list-view","Usuario ${arrayUsuarios[posicionItemSelecionado].toString()}")

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        //editar, y el egundo caso es eiminar.
        var UsuarioSelect = arrayUsuarios[posicionItemSelecionado]
        return when(item?.itemId){

            //Editar
            R.id.mi_Editar -> {
                Log.i("list-view","Editar ${UsuarioSelect}")
                //Log.i("list-view2","Editar")
                abrirActividadConParametros(EditarAutor::class.java,UsuarioSelect)

                return true
            }

            //Eliminar
            R.id.mi_Eliminar -> {
                Log.i("list-view","Eliminar ${UsuarioSelect}")
                val arrayCancionesEliminar = arrayListOf<String>()
                val db = Firebase.firestore
                var refCancion = db
                    .collection("canciones")
                refCancion
                    .whereEqualTo("usuario_uid",UsuarioSelect.uid.toString())
                    .get()
                    .addOnSuccessListener {
                        for(cancion in it){
                            arrayCancionesEliminar.add(cancion.id.toString())
                            refCancion.document(cancion.id).delete()
                                .addOnSuccessListener {
                                    Log.d("list-view", "DocumentSnapshot successfully deleted!")
                                }
                                .addOnFailureListener { e -> Log.w("list-view", "Error deleting document", e) }
                        }
                    }
                    .addOnFailureListener{}



                var refUsuario = db
                    .collection("autores")

                refUsuario.document(UsuarioSelect.uid.toString())
                    .delete()
                    .addOnSuccessListener {
                        adapter?.remove(adapter!!.getItem(posicionItemSelecionado));
                        adapter?.notifyDataSetChanged()
                        Log.d("list-view", "DocumentSnapshot successfully deleted!")



                    }
                    .addOnFailureListener { e -> Log.w("list-view", "Error deleting document", e) }



                return true }

            R.id.mi_Ver -> {

                abrirActividadConParametros(VerCanciones::class.java,UsuarioSelect)

                Log.i("list-view","Ver Canciones ${UsuarioSelect}")

                return true }

            else -> super.onContextItemSelected(item)
        }
    }

    fun quemarDatos(){
        val arrayUsuarios1 =  arrayListOf<AutorDTO>()
        arrayUsuarios1.add(
            AutorDTO(
                null,
                "1111111111",
                "Nombre1",
                "Apellido1",


            )
        )
        arrayUsuarios1.add(
            AutorDTO(
                null,
                "2222222222",
                "Nombre2",
                "Apellido2",



            )
        )
        arrayUsuarios1.add(
            AutorDTO(
                null,
                "3333333333",
                "Nombre3",
                "Apellido3",


            ))

        for(objeto in arrayUsuarios1){
            val nuevoUsuario = hashMapOf<String,Any>(
                "nombre" to objeto.nombre!!,
                "pais" to objeto.pais!!,
                "edad" to objeto.edad!!,

            )
            val db = Firebase.firestore
            val referencia = db.collection("usuario").document()
            referencia.set(nuevoUsuario)
        }






    }

}