package com.example.examen2b

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.example.examen2b.dto.AutoresDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Autores : AppCompatActivity() {
    var posicionItemSeleccionado=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_autores)
        val btnCrearAutor = findViewById<Button>(R.id.btn_crear)

        btnCrearAutor.setOnClickListener {
            crearAutor()

        }
        val btnMostrarAutor = findViewById<Button>(R.id.btn_mostrar_autores)
        btnMostrarAutor.setOnClickListener {
            abrirActividad(VerAutores::class.java)
            //cargarAutores()


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



    fun crearAutor( ){
        val editTextNombre = findViewById<EditText>(R.id.et_nombre)
        val editTextPais = findViewById<EditText>(R.id.et_Pais)
        val editTextEdad = findViewById<EditText>(R.id.ed_edad)
        val nuevoAutor = hashMapOf<String, Any>(
            "nombre" to editTextNombre.text.toString(),
            "pais" to editTextPais.text.toString(),
            "edad" to editTextEdad.text.toString().toInt()

        )
        val db= Firebase.firestore
        val referencia = db.collection("autores")
        referencia
            .add(nuevoAutor)
            .addOnSuccessListener {

                Toast.makeText(getApplicationContext(),"Autor creado correctamente", Toast.LENGTH_SHORT).show()
                editTextNombre.text.clear()
                editTextPais.text.clear()
                editTextEdad.text.clear()
            }
            .addOnFailureListener { Toast.makeText(getApplicationContext(),"El autor no se ha creado",Toast.LENGTH_SHORT).show() }



    }
}