package com.example.examen_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import android.widget.ArrayAdapter as ArrayAdapter

class Autor : AppCompatActivity() {
    var posicionItemSeleccionado = 0
    var CODIGO_RESPUESTA_INTENT_EXPLICITO=402

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_autor)
       EBaseDatos.TablaAutor= ESqLiteHelperAutor(this)


        val idAutor= findViewById<TextView>(R.id.txtId)
        val nombre = findViewById<EditText>(R.id.txtNombre)
        val pais = findViewById<EditText>(R.id.txtPais)
        val edad = findViewById<EditText>(R.id.txtFechaNaci)


         val botonCrearAutor = findViewById<Button>(R.id.btn_crear)

            botonCrearAutor.setOnClickListener{

                if (EBaseDatos.TablaAutor != null) {
                    EBaseDatos.TablaAutor?.crearAutor(
                        idAutor.text.toString().toInt(),
                        nombre.text.toString(),
                        pais.text.toString(),
                        edad.text.toString()

                    )
                    Toast.makeText(getApplicationContext(),"usuario creado",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(getApplicationContext(),"ERROR, usuario no creado",Toast.LENGTH_SHORT).show()
                }

                idAutor.setText("")
                nombre.setText("")
                pais.setText("")
                edad.setText("")

            }
        val botonMostrarAutor=findViewById<Button>(R.id.btn_mostrar)
        botonMostrarAutor.setOnClickListener{
            abrirActividad(VerAutores::class.java)
        }
        // Hacer que cuando debemos clic en editar se abra algo o
        // realice alguna accion
     //   registerForContextMenu(listaAutores)




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