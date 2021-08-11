package com.example.deber02_skype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class IRaNotificaciones : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ira_notificaciones)

        val btnIrRecyclerView=findViewById<ImageButton>(R.id.btnIrRecyclerView)
        btnIrRecyclerView.setOnClickListener{
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