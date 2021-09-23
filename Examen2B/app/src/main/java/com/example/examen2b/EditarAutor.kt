package com.example.examen2b

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.examen2b.dto.AutorDTO
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EditarAutor : AppCompatActivity() {
    var usuario: AutorDTO?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_autor)
        usuario= intent.getParcelableExtra<AutorDTO>("autores")

        val nombre = findViewById<EditText>(R.id.ti_actualizar_cedula)
        val pais = findViewById<EditText>(R.id.ti_actualizar_nombre)
        val edad = findViewById<EditText>(R.id.ti_actualizar_apellido)


        nombre.addTextChangedListener(crearusuarioTextWatcher)
        pais.addTextChangedListener(crearusuarioTextWatcher)
        edad.addTextChangedListener(crearusuarioTextWatcher)


        nombre.setText(usuario?.nombre)
        pais.setText(usuario?.pais)
        edad.setText(usuario?.edad)




        val botonactualizarUsuario = findViewById<Button>(R.id.btn_actualizar_Usuario)
        botonactualizarUsuario.isEnabled=false
        botonactualizarUsuario
            .setOnClickListener {

                actualizarUsuario()

            }
    }
    fun actualizarUsuario(){
        val nombre = findViewById<EditText>(R.id.ti_actualizar_cedula)
        val pais = findViewById<EditText>(R.id.ti_actualizar_nombre)
        val edad = findViewById<EditText>(R.id.ti_actualizar_apellido)


        var objetoUsuarioDto = AutorDTO(
            null,
            nombre.text.toString(),
            pais.text.toString(),
            edad.text.toString(),


        )


        val nuevoUsuario = hashMapOf<String,Any>(
            "nombre" to objetoUsuarioDto.nombre!!,
            "pais" to objetoUsuarioDto.pais!!,
            "edad" to objetoUsuarioDto.edad!!,

        )

        val db = Firebase.firestore
        val referencia = db.collection("autores")
            .document(usuario?.uid!!)

        db.runTransaction {  transaction ->
            //val documentoActual = transaction.get(referencia)
            transaction.update(referencia, nuevoUsuario)
        }
            .addOnSuccessListener {
                abrirActividad(MainActivity::class.java)
                Log.i("transaccion", "Transaccion Completa")
            }
            .addOnFailureListener{
                Log.i("transaccion", "ERROR")
            }
    }

    private val crearusuarioTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val nombre = findViewById<EditText>(R.id.ti_actualizar_cedula).text.toString().trim()
            val pais = findViewById<EditText>(R.id.ti_actualizar_nombre).text.toString().trim()
            val edad = findViewById<EditText>(R.id.ti_actualizar_apellido).text.toString().trim()


            val botonactualizarUsuario = findViewById<Button>(R.id.btn_actualizar_Usuario)


            botonactualizarUsuario.isEnabled = (nombre.isNotEmpty() &&
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