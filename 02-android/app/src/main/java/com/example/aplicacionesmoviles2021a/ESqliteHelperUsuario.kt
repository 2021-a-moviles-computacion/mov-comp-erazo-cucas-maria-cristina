package com.example.aplicacionesmoviles2021a

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.aplicacionesmoviles2021a.EUsuarioBDD

class ESqliteHelperUsuario(
    contexto: Context?
) : SQLiteOpenHelper(
    contexto,
    "moviles",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptCrearTablaUsuario =
            """
            CREATE TABLE USUARIO (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(50),
                descripcion varchar(50)
            )
        """.trimIndent()
        Log.i("bbd", "Creando la tabla de usuario")
        db?.execSQL(scriptCrearTablaUsuario)
    }


    fun crearUsuarioFormulario(
        nombre: String,
        descripcion: String
    ): Boolean {
//        val conexionExcritura = this.writableDatabase
        val conexionExcritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("descripcion", descripcion)
        val resultadoEscritura: Long = conexionExcritura
            .insert(
                "USUARIO",
                null,
                valoresAGuardar
            )
        conexionExcritura.close()
        return if (resultadoEscritura.toInt() == -1) false else true
    }

    fun consultarUsuarioPorId(id: Int): EUsuarioBDD {

        val scriptConsultarUsuario = "SELECT * FROM USUARIO WHERE ID = ${id}"
//        val baseDatosLectura = this.readableDatabase
        val baseDatosLectura = readableDatabase
        val resultaConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarUsuario,
            null
        )
        val existeUsuario = resultaConsultaLectura.moveToFirst()
        // val arregloUsuario = arrayListOf<EUsuarioBDD>()
        val usuarioEncontrado = EUsuarioBDD(0, "", "")
        if (existeUsuario) {
            do {
                val id = resultaConsultaLectura.getInt(0) // Columna indice 0 -> ID
                val nombre = resultaConsultaLectura.getString(1) // Columna indice 1 -> NOMBRE
                val descripcion =
                    resultaConsultaLectura.getString(2) // Columna indice 2 -> DESCRIPCION
                if (id != null) {
                    usuarioEncontrado.id = id
                    usuarioEncontrado.nombre = nombre
                    usuarioEncontrado.descripcion = descripcion
                    // arregloUsuario.add(usuarioEncontrado)
                }
            } while (resultaConsultaLectura.moveToNext())
        }
        resultaConsultaLectura.close()
        baseDatosLectura.close()
        return usuarioEncontrado
    }


    fun eliminarUsuarioFormulari(id: Int): Boolean {
      //  val conexionEscritura = this.writableDatabase
        val conexionEscritura= writableDatabase
        val resultadoEliminacion = conexionEscritura
            .delete(
                "USUARIO",
                "id=?",
                arrayOf(
                    id.toString()
                )
            )
        conexionEscritura.close()
        return if (resultadoEliminacion.toInt()==-1)false else true

    }

    fun actualizarUsuarioFormulario(
        nombre: String,
        descripcion: String,
        idActualizar: Int
    ): Boolean{
        val conexionEscritura= writableDatabase
        val valoresActualizar= ContentValues()
        valoresActualizar.put("nombre", nombre)
        valoresActualizar.put("descripcion", descripcion)
        val resultadoActualizacion= conexionEscritura
            .update(
                "USUARIO",
                valoresActualizar,
                "id=?",
                arrayOf (
                        idActualizar.toString()
                )//parametros clausula where

            )
        conexionEscritura.close()
        return if(resultadoActualizacion==-1)false else true

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}
}