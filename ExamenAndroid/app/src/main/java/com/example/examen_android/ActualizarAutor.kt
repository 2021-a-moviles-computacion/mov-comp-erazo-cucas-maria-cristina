package com.example.examen_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class ActualizarAutor : AppCompatActivity() {
  //  var adapter: ArrayAdapter<BAutor>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_actualizar_autor)
      EBaseDatos.TablaAutor = ESqLiteHelperAutor(this)
      val nombre = findViewById<EditText>(R.id.txtActualizarNombre)
      val pais = findViewById<EditText>(R.id.txtActualizarPais)
      val edad = findViewById<EditText>(R.id.txtActualizarEdad)
      val autor = intent.getParcelableExtra<BAutor>("usuario")//agarro el autor que me envio VerAutories


      val botonActualizar = findViewById<Button>(R.id.btn_actualizarAutor)
      botonActualizar.setOnClickListener {

                if (EBaseDatos.TablaAutor != null) {
                    if (autor != null) {
                        EBaseDatos.TablaAutor!!.actualizarAutor(
                            nombre.text.toString(),
                            pais.text.toString(),
                            edad.text.toString(),
                            2
                        )
                    }
                    Toast.makeText(getApplicationContext(), "autor actualizado", Toast.LENGTH_SHORT)
                        .show()
                }else{
                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT)
                        .show()
                }

//          if (autor != null) {
//
//              val resultado = EBaseDatos.TablaAutor!!.actualizarAutor(
//                  "DAYANARA PERALTA",
//                  "ECUADOR",
//                  "25",
//
//                  9
//              )
//              Toast.makeText(getApplicationContext(), "autor actualizado", Toast.LENGTH_SHORT)
//                        .show()
//              if(resultado){
//                  abrirActividad(VerAutores::class.java)
//              }


              //  abrirActividad(VerAutores::class.java)

//          }else{
//              Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT)
//                        .show()
//          }
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