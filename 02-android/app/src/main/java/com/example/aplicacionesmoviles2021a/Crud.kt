package com.example.aplicacionesmoviles2021a

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class Crud : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud)

        val txtNombre= findViewById<EditText>(R.id.txtNombre)
        val txtId= findViewById<EditText>(R.id.txtId)
        val txtDescripcion= findViewById<EditText>(R.id.txtDescripcion)


        val botonConsultar = findViewById<Button>(R.id.btn_consultar)
        botonConsultar.setOnClickListener{
            val usuarioEncontrado = EBaseDeDatos?.TablaUsuario?.consultarUsuarioPorId(30)
            Log.i("bdd", "id:${usuarioEncontrado?.id} Nombre:${usuarioEncontrado?.nombre}"+
                    "Descripcion: ${usuarioEncontrado?.descripcion}")
        }

        val botonCrear = findViewById<Button>(R.id.btn_guardar)
        botonCrear.setOnClickListener{

            //val resultado = EBaseDeDatos.TablaUsuario?.crearUsuarioFormulario("${txtNombre.text.toString()}","${txtDescripcion.text.toString()}")
           val resultado = EBaseDeDatos.TablaUsuario?.crearUsuarioFormulario("MarC","HOLA QUE TAL")
            if(resultado != null){
                if(resultado){
                    Log.i("bdd","El registro se ha creado")


                }else{
                    Log.i("bdd","error")
                }
            }

        }

        val botonActualizar= findViewById<Button>(R.id.btn_actualizar)
        botonActualizar.setOnClickListener{
           // val resultado = EBaseDeDatos.TablaUsuario?.actualizarUsuarioFormulario("${txtNombre.text.toString()}", "${txtDescripcion.text.toString()}",1)
            val resultado = EBaseDeDatos.TablaUsuario?.actualizarUsuarioFormulario("cristina", "soy tu amiga",30)
            if(resultado != null){
                if(resultado){
                    Log.i("bdd","Actualizacion exitosa")


                }else{
                    Log.i("bdd","error")
                }
            }

        }

        val botonEliminar = findViewById<Button>(R.id.btn_eliminar)
        botonEliminar.setOnClickListener{
            val resultado= EBaseDeDatos.TablaUsuario?.eliminarUsuarioFormulari(30)
            if(resultado != null){
                if(resultado){
                    Log.i("bdd","Eliminacion exitosa")

                }else{
                    Log.i("bdd","error")
                }
            }
        }

        fun limpiar(){
            txtNombre.setText("")
            txtDescripcion.setText("")
        }
    }


    
}