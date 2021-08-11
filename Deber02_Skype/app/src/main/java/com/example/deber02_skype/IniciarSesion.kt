package com.example.deber02_skype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class IniciarSesion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iniciar_sesion)

        val btnSiguiente = findViewById<Button>(R.id.btn_siguiente)
        btnSiguiente.setOnClickListener{
            abrirActividad(IniciarContrasenia::class.java)
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