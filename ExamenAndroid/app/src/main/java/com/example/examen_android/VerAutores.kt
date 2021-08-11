package com.example.examen_android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class VerAutores : AppCompatActivity() {


    var adapter1: ArrayAdapter<BCancion>? = null
    var adapter: ArrayAdapter<BAutor>? = null
    var posicionItemSeleccionado=0
    var CODIGO_RESPUESTA_INTENT_EXPLICITO=402


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_autores)
       EBaseDatos.TablaAutor = ESqLiteHelperAutor(this)


      //  lista= EBaseDatos.TablaAutor?.llenarlistaAutores()
        //cargarListView()
        val listaVAutores= findViewById<ListView>(R.id.lv_listaAutores)

        adapter= ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            EBaseDatos.TablaAutor!!.llenarlistaAutores()
        )
        listaVAutores.adapter=adapter
        registerForContextMenu(listaVAutores)

    }
    fun actualizaListView()
    {
        adapter= ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            EBaseDatos.TablaAutor!!.llenarlistaAutores()
        )
        findViewById<ListView>(R.id.lv_listaAutores).adapter=adapter
        registerForContextMenu(findViewById<ListView>(R.id.lv_listaAutores))

    }
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        var inflater= menuInflater
        inflater.inflate(R.menu.menu, menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        posicionItemSeleccionado =id
        Log.i("bdd", "Autor ${posicionItemSeleccionado}")
        //  Log.i("bdd","Autor ${BBaseDatosMemoria.arregloBAutor[id]}")
    }


    //cuando doy clic en eliminar o editar o ver HACER ALGO
    override fun onContextItemSelected(item: MenuItem): Boolean {



        return when (item?.itemId) {
            // Editar


            R.id.mi_Editar -> {

                val lista: BAutor =EBaseDatos.TablaAutor!!.consultarAutores()[posicionItemSeleccionado]//envio ese autor
                Log.i("bdd","Editar ${lista}")
                //val autor= lista[posicionItemSeleccionado]
                abrirActividadConParametros(ActualizarAutor::class.java, lista)
                return true
            }




            // Eliminar
            R.id.mi_Eliminar -> {
                var lista = EBaseDatos.TablaAutor!!.consultarAutores()[posicionItemSeleccionado]
                Log.i("bdd","Eliminaer ${lista}")
                EBaseDatos.TablaAutor!!.eliminarAutor(lista.idAutor)
                Toast.makeText(getApplicationContext(),"eliminado", Toast.LENGTH_SHORT).show()

               adapter?.notifyDataSetChanged()

                    return true
                }


            R.id.mi_Ver -> {
                var lista = EBaseDatos.TablaAutor!!.consultarAutores()[posicionItemSeleccionado]

                abrirActividadConParametros(VerCanciones::class.java,lista)

                Log.i("bdd","${lista.nombre}")
                adapter?.notifyDataSetChanged()

                return true


            }

            else -> super.onContextItemSelected(item)
        }
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
        usuario: BAutor,
    ){
        val intentExplicito = Intent(
            this,
            clase,
        )
        //intentExplicito.putExtra("nombre","Adrian")
        intentExplicito.putExtra("usuario",usuario)
        startActivityForResult(intentExplicito,CODIGO_RESPUESTA_INTENT_EXPLICITO)

    }
}