package com.example.deber02_skype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val botonCrearCuenta= findViewById<Button>(R.id.btn_iniciarSesion)
        botonCrearCuenta.setOnClickListener{
            abrirActividad(CrearCuenta::class.java)
        }

        val botonIniciarSesion= findViewById<Button>(R.id.btn_iniciarSesion)
        botonIniciarSesion.setOnClickListener{
            abrirActividad(IniciarSesion::class.java)
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