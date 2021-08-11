package com.example.deber02_skype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class ChatPrincipal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_principal)

        val botonRegresarInterfazPrincipal= findViewById<ImageView>(R.id.tv_atras_listaContactos)
        botonRegresarInterfazPrincipal.setOnClickListener {
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