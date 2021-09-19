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
import android.widget.ListView
import android.widget.Toast
import com.example.examen2b.dto.AutoresDTO
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class VerAutores : AppCompatActivity() {
    var posicionItemSeleccionado=0
    lateinit var arregloAutores: ArrayList<AutoresDTO>
     var adaptadorAutores: ArrayAdapter<AutoresDTO>?= null
    //var adapter: ArrayAdapter<Autores>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_autores)
        this.arregloAutores = traerDatosAutoresFFirebase()
        val listaAutores = findViewById<ListView>(R.id.lv_autores)
        adaptadorAutores= ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arregloAutores
        )
        listaAutores.adapter= adaptadorAutores
        registerForContextMenu(listaAutores)





    }



    fun traerDatosAutoresFFirebase(): ArrayList<AutoresDTO>{
        val db = Firebase.firestore
        val arregloAutores= arrayListOf<AutoresDTO>()

            val referencia= db.collection("autores")
            .get()
            referencia
            .addOnSuccessListener {
                for (document in it){
                    var autorCargado = document.toObject(AutoresDTO::class.java)

                         arregloAutores.add(
                             AutoresDTO
                                 (
                                 autorCargado.edad,
                                 autorCargado.nombre,
                                 autorCargado.pais,

                             )
                         )

                    adaptadorAutores?.notifyDataSetChanged()



                }
            }
            .addOnFailureListener {
                Log.i("Error getting documents: ","no se obtuvieron los datos")
            }
        return  arregloAutores
    }



    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        var inflater= menuInflater
        inflater.inflate(R.menu.menu_autores, menu)

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

              return true
            }

            // Eliminar
            R.id.mi_Eliminar -> {


                return true
            }

            R.id.mi_Ver -> {

                abrirActividad(VerCanciones::class.java)
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
}