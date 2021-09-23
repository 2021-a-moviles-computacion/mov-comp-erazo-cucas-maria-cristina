package com.example.examen2b

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import com.example.examen2b.dto.AutorDTO
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CrearAutores : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_autores)

        val nombre = findViewById<EditText>(R.id.et_nombre)
        val pais = findViewById<EditText>(R.id.et_pais)
        val edad = findViewById<EditText>(R.id.et_edad)


        nombre.addTextChangedListener(crearusuarioTextWatcher)
        pais.addTextChangedListener(crearusuarioTextWatcher)
        edad.addTextChangedListener(crearusuarioTextWatcher)


        val botonCrearUsuario = findViewById<Button>(R.id.btn_crearAutor)
        botonCrearUsuario.isEnabled=false
        botonCrearUsuario
            .setOnClickListener {

                crearAutor()

            }


    }

    fun crearAutor(){
        val nombre = findViewById<EditText>(R.id.et_nombre)
        val pais = findViewById<EditText>(R.id.et_pais)
        val edad = findViewById<EditText>(R.id.et_edad)


        var objetoAutor = AutorDTO(
            null,
            nombre.text.toString(),
            pais.text.toString(),
            edad.text.toString(),


        )
        val nuevoAutor = hashMapOf<String,Any>(
            "nombre" to objetoAutor.nombre!!,
            "pais" to objetoAutor.pais!!,
            "edad" to objetoAutor.edad!!
        )
        val db = Firebase.firestore
        val referencia = db.collection("autores")
        referencia
            .add(nuevoAutor)
            .addOnSuccessListener {
                //editTextNombre.text.clear()
                abrirActividad(MainActivity::class.java)
            }
            .addOnFailureListener{}
    }

    private val crearusuarioTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val nombre = findViewById<EditText>(R.id.et_nombre).text.toString().trim()
            val pais = findViewById<EditText>(R.id.et_pais).text.toString().trim()
            val edad = findViewById<EditText>(R.id.et_edad).text.toString().trim()



            val botonCrearUsuario = findViewById<Button>(R.id.btn_crearAutor)

            botonCrearUsuario.isEnabled = (
                    nombre.isNotEmpty() &&
                    pais.isNotEmpty() &&
                    edad.isNotEmpty() )
                           }

        override fun afterTextChanged(s: Editable) {}
    }
    private fun abrirActividad(clase: Class<*>){
        val intentExplicito = Intent( //Intent es una clase, solamente para que este bien contextualizado.
            this,
            clase
        )
        startActivity(intentExplicito) //Lo heredamos de la clase.
    }
}