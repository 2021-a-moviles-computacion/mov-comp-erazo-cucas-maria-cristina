package com.example.aplicacionesmoviles2021a

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*

class BListView : AppCompatActivity() {
    var posicionItemSeleccionado = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view)
        val arregloNumeros= BBaseDatosMemoria.arregloBEntrenador

        //val arregloNumeros = arrayListOf<BEntrenador>(
        //    BEntrenador("Adrian","a@a.com"),
        //    BEntrenador("Joseph","b@b.com"),
         //   BEntrenador("Mary","c@c.com")
        //)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arregloNumeros

        )
        val listVienEjemplo= findViewById<ListView>(R.id.ltv_ejemplo)
        listVienEjemplo.adapter= adaptador

        val botonAnadirNumero= findViewById<Button>(R.id.btn_anadir_numero)
        botonAnadirNumero.setOnClickListener{
        anadirItemsAllListView(BEntrenador("Prueba","d@d.com"),
          arregloNumeros,
          adaptador
        )
        }

        listVienEjemplo
            .setOnItemLongClickListener{adapterView, view, posicion, id ->
                Log.i("list-view", "Dio clic ${posicion}")
                return@setOnItemLongClickListener true
            }
        registerForContextMenu(listVienEjemplo)
    }

    fun anadirItemsAllListView(
        valor: BEntrenador,
        arreglo: ArrayList<BEntrenador>,
       adaptador: ArrayAdapter<BEntrenador>
    ){
      arreglo.add(valor)
      adaptador.notifyDataSetChanged()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        posicionItemSeleccionado = id
        Log.i("list-view", "List wien ${posicionItemSeleccionado}")
    }

    override fun OnContextItemSelected(item: MenuItem): Boolean{
        return when(item?.itemId){
            //Editar
                R.id.mI_Editar ->{
                    Log.i("list-view", "Editar ${BBaseDatosMemoria.arregloBEntrenador[ posicionItemSeleccionado ]}")
                    return true
                }
                //Eliminar
            R.id.mI_eliminar ->{
                Log.i("list-view", "Editar ${BBaseDatosMemoria.arregloBEntrenador[
                        posicionItemSeleccionado
                ]}")
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}
