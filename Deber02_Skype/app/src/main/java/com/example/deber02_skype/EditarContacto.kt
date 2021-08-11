package com.example.deber02_skype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class EditarContacto : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_contacto)
        val regresarChatPrincipal=findViewById<ImageView>(R.id.regresarAChatPrincipal)
        regresarChatPrincipal.setOnClickListener {
            abrirActividad(ChatPrincipal::class.java)
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