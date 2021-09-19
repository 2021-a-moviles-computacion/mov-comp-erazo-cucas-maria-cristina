package com.example.examen2b

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnInterfazPrincipal = findViewById<Button>(R.id.btn_ir_interfaz_principal)
        btnInterfazPrincipal.setOnClickListener {
            val intent = Intent(
                this,
                Autores::class.java
            )
            startActivity(intent)
        }
    }
}