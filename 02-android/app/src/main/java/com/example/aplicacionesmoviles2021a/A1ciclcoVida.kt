package com.example.aplicacionesmoviles2021a

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.TextView

class A1ciclcoVida : AppCompatActivity() {
    var numero=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a1ciclco_vida)

        Log.i("ciclo-vida","onCreate")
        val textViewCicloVida= findViewById<TextView>(
            R.id.txv_ciclo_vida
        )
        textViewCicloVida.text= numero.toString()
        val buttonACicloVida= findViewById<TextView>(
            R.id.txv_ciclo_vida
        )
        buttonACicloVida.setOnClickListener{  aumentarNumero()  }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.run{
            putInt("NUMERO GUARDADO", numero)
        }
        super.onSaveInstanceState(outState)
        Log.i("ciclo-vida", "OnSaveInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val numeroRecuperado: Int?= savedInstanceState.getInt("numeroGuardado")
        if(numeroRecuperado != null){
            Log.i("ciclo-vida", "LLego numero ${numeroRecuperado}")
            //this.numero= numero
            numero= numeroRecuperado
            val textViewACicloVida = findViewById<TextView>(
                R.id.txv_ciclo_vida
            )
            Log.i("ciclo-vida","")
        }
    }






    fun aumentarNumero(){
        numero= numero+1
        val textViewCicloVida = findViewById<TextView>(
                R.id.txv_ciclo_vida
                )
        textViewCicloVida.text= numero.toString()
    }

    override fun onStart(){
        super.onStart()
        Log.i("ciclo-vida","onStart")
    }

    override fun onRestart(){
        super.onRestart()
        Log.i("ciclo-vida","onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.i("ciclo-vida","onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i("ciclo-vida","onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i("ciclo-vida","onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("ciclo-vida","onDestroy")
    }
}