package com.example.examen_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class CrearCanciones : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_canciones)

        EBaseDatos.TablaAutor= ESqLiteHelperAutor(this)
        val autor = intent.getParcelableExtra<BAutor>("usuario")

        // llamando a los componentes
        val idCancion= findViewById<EditText>(R.id.txt_idCancion)
        val titulo= findViewById<EditText>(R.id.txtTitulo)
        val genero= findViewById<EditText>(R.id.txtGenero)
        val duracion= findViewById<EditText>(R.id.txtDuracion)


        val BtncrearCanciones= findViewById<Button>(R.id.btn_guardarCancion)
        BtncrearCanciones.setOnClickListener{
            if(EBaseDatos.TablaAutor!=null){


                    EBaseDatos.TablaAutor!!.crearCancion(
                        5,
                        idCancion.text.toString().toInt(),
                        titulo.text.toString(),
                        genero.text.toString(),
                        duracion.text.toString()


                    )


                Toast.makeText(getApplicationContext(),"cancion creada", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show()
            }
            idCancion.setText("")
            titulo.setText("")
            genero.setText("")
            duracion.setText("")


        }
    }
}