package com.example.examen2b

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Canciones : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_canciones)
        val btnCrearCancion= findViewById<Button>(R.id.btn_crearCancion)
        btnCrearCancion.setOnClickListener {
            crearCancion()
        }
    }

    fun crearCancion(){
        val tituloCancion= findViewById<EditText>(R.id.et_titulo_cancion)
        val generoCancion = findViewById<EditText>(R.id.et_genero_cancion)
        val duracionCancion = findViewById<EditText>(R.id.et_duracion_cancion)
        val nuevaCancion = hashMapOf<String, Any>(
            "tituloCancion" to tituloCancion.text.toString(),
            "generoCancion" to generoCancion.text.toString(),
            "duracionCancion" to duracionCancion.text.toString()
        )
        val db= Firebase.firestore
        val referencia= db.collection("canciones")
        referencia.add(nuevaCancion)
            .addOnSuccessListener {

                Toast.makeText(getApplicationContext(),"Cancion creada exitosamente", Toast.LENGTH_SHORT).show()
                tituloCancion.text.clear()
                generoCancion.text.clear()
                duracionCancion.text.clear()
            }
            .addOnFailureListener {Toast.makeText(getApplicationContext(),"Error, la cancion no se ha creado",Toast.LENGTH_SHORT).show()   }
    }
}