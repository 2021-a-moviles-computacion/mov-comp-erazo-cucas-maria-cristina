package com.example.examen2b

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.example.examen2b.dto.AutoresDTO
import com.example.examen2b.dto.CancionesDTO
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class VerCanciones : AppCompatActivity() {
    var posicionItemSeleccionado=0
    lateinit var arregloCanciones: ArrayList<CancionesDTO>
    var adaptadorCanciones: ArrayAdapter<CancionesDTO>?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_canciones)
        this.arregloCanciones = traerDatosCancionesFirebase()
        val listaCanciones = findViewById<ListView>(R.id.lv_vercanciones)
        adaptadorCanciones= ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arregloCanciones
        )
        listaCanciones.adapter= adaptadorCanciones
        registerForContextMenu(listaCanciones)

        val crearCancion= findViewById<Button>(R.id.btn_crear_canciones)
        crearCancion.setOnClickListener {
            abrirActividad(Canciones::class.java)
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

    fun traerDatosCancionesFirebase(): ArrayList<CancionesDTO>{
        val db = Firebase.firestore
        val arregloCanciones= arrayListOf<CancionesDTO>()

        val referencia= db.collection("canciones")
            .get()
        referencia
            .addOnSuccessListener {
                for (document in it){
                    var cancionCargada = document.toObject(CancionesDTO::class.java)

                    arregloCanciones.add(
                        CancionesDTO(
                            cancionCargada.tituloCancion,
                            cancionCargada.generoCancion,
                            cancionCargada.duracionCancion
                        )
                    )

                    adaptadorCanciones?.notifyDataSetChanged()



                }
            }
            .addOnFailureListener {
                Log.i("Error getting documents: ","no se obtuvieron los datos")
            }
        return  arregloCanciones
    }
}