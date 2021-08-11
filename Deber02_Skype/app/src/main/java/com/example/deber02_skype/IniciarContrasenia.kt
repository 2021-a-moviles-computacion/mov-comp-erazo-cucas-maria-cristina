package com.example.deber02_skype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class IniciarContrasenia : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iniciar_contrasenia)
        val iniciarSesion= findViewById<Button>(R.id.btnIniciarSesion)
        iniciarSesion.setOnClickListener{
            abrirActividad(GRecyclerView::class.java)
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