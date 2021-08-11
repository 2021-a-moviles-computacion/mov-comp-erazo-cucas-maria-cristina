package com.example.examen_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class ActualizarCancion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_cancion)
        EBaseDatos.TablaAutor = ESqLiteHelperAutor(this)
        val titulo= findViewById<TextView>(R.id.editTitulo)
        val genero=findViewById<TextView>(R.id.editGenero)
        val duracion= findViewById<TextView>(R.id.editDuracion)
        val cancion = intent.getParcelableExtra<BCancion>("cancion")

        val btnActualizarCancion= findViewById<Button>(R.id.btn_actualizar_Cancion)
        btnActualizarCancion.setOnClickListener{
            if (EBaseDatos.TablaAutor != null) {

                    EBaseDatos.TablaAutor!!.actualizarCancion(
                        titulo.text.toString(),
                        genero.text.toString(),
                        duracion.text.toString(),
                        502
                    )

                Toast.makeText(getApplicationContext(), "cancion actualizada", Toast.LENGTH_SHORT)
                    .show()
            }else{
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }
}