package com.example.examen_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*

class VerCanciones : AppCompatActivity() {
    var adapter: ArrayAdapter<BCancion>? = null

   var posicionItemSeleccionado=0
    val CODIGO_RESPUESTA_INTENT_EXPLICITO = 401


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_cancion)
        val autor = intent.getParcelableExtra<BAutor>("usuario")
       // val autor = intent.getParcelableExtra<BAutor>("usuario")

        val listaCanciones= findViewById<ListView>(R.id.lv_listaCanciones)
        val btnCrearCanciones = findViewById<Button>(R.id.btn_crearcancion)
        btnCrearCanciones.setOnClickListener{
            abrirActividad(CrearCanciones::class.java)
        }



        if(EBaseDatos.TablaAutor!=null) {
            if (autor != null) {
                EBaseDatos.TablaAutor!!.consultarCancionesPorIdAutor(5)
            }

            if (autor != null) {
                adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    EBaseDatos.TablaAutor!!.llenarlistaCanciones(5)

                )
                adapter?.notifyDataSetChanged()
            }



            Log.i("bdd","datos llenos")
                listaCanciones.adapter = adapter
                registerForContextMenu(listaCanciones)



        }






    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        var inflater= menuInflater
        inflater.inflate(R.menu.menu1, menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        posicionItemSeleccionado =id
        Log.i("bdd", "Cancion ${posicionItemSeleccionado}")
        //  Log.i("bdd","Autor ${BBaseDatosMemoria.arregloBAutor[id]}")
    }
    fun abrirActividad(
        clase: Class<*>
    ) {
        val intentExplicito = Intent(
            this,
            clase
        )
        // this.startActivity(intent)
        startActivity(intentExplicito)
    }
    fun abrirActividadConParametros(
        clase: Class<*>,
       cancion: BCancion,
    ){
        val intentExplicito = Intent(
            this,
            clase
        )
        intentExplicito.putExtra("cancion",cancion)
        startActivityForResult(intentExplicito,CODIGO_RESPUESTA_INTENT_EXPLICITO)
    }




    //cuando doy clic en eliminar o editar o ver HACER ALGO
    override fun onContextItemSelected(item: MenuItem): Boolean {



        return when (item?.itemId) {
            // Editar


            R.id.editar_Cancion -> {

                val lista =EBaseDatos.TablaAutor!!.consultarCanciones()[posicionItemSeleccionado]
                Log.i("bdd","Editar ${lista}")
                //val autor= lista[posicionItemSeleccionado]
                abrirActividadConParametros(ActualizarCancion::class.java,lista)
                Log.i("bdd","${lista.titulo}")
                return true
            }




            // Eliminar
            R.id.eliminar_cancion -> {
                val autor = intent.getParcelableExtra<BAutor>("usuario")

                val lista = EBaseDatos.TablaAutor!!.consultarCancionesPorIdAutor(5)[posicionItemSeleccionado]
                Log.i("bdd","Eliminar ${lista}")
                EBaseDatos.TablaAutor!!.eliminarCancion(lista.idCancion)
                Toast.makeText(getApplicationContext(),"eliminado", Toast.LENGTH_SHORT).show()
                Log.i("bdd","se elimino ?")
                adapter?.notifyDataSetChanged()
                adapter?.remove(adapter!!.getItem(posicionItemSeleccionado));


                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

        }