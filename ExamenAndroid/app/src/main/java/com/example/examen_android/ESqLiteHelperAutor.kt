package com.example.examen_android

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.core.content.contentValuesOf

class ESqLiteHelperAutor(
    contexto: Context?
) : SQLiteOpenHelper (
    contexto,
    "mi_base5",
    null,
    2
        ){
    override fun onCreate(db: SQLiteDatabase?) {
//        val scriptDat=
//            """
//                DROP DATABASE music
//
//           """.trimIndent()
//        db?.execSQL(scriptDat)
//        Log.i("bdd","tabla borrada")
//        val scriptDropDatabase=
//            """
//                DROP TABLE AUTORES
//            """.trimIndent()
//        db?.execSQL(scriptDropDatabase)
//        Log.i("bdd","base borrada")
        val sriptCrearTablaAutor =
            """
                CREATE TABLE AUTORES(
                idAutor INTEGER PRIMARY KEY,
                nombre VARCHAR(30),
                pais VARCHAR(30),
                edad VARCHAR(3)
                );
                """.trimIndent()
        Log.i("bdd","Creando la tabla autor")
        db?.execSQL(sriptCrearTablaAutor)
        val scriptCTablaCanciones=
            """
                CREATE TABLE CANCION(
                idCancion INTEGER PRIMARY KEY,
                idAutor INTEGER,
                titulo VARCHAR(20),
                genero VARCHAR(20),
                duracion VARCHAR(10),
                FOREIGN KEY (idAutor) REFERENCES AUTORES(idAutor)
                );
            """.trimIndent()
        Log.i("bdd","Creando la tabla cancion")
        db?.execSQL(scriptCTablaCanciones)

        val scriptIngresarAutores=
            """
                INSERT INTO AUTORES(idAutor, nombre, pais, edad)
                VALUES(1,"LEO DAN","MEXICO","87"),
                       (2,"ANA GABRIEL", "MEXICO", "68"),
                       (3,"DAYANARA PERALTA", "ECUADOR", "25"),
                       (4,"KAROL G", "COLOMBIA", "28")
            """.trimIndent()
        db?.execSQL(scriptIngresarAutores)

        val scriptIngresarCanciones=
            """
                INSERT INTO CANCION(idCancion,idAutor, titulo, genero, duracion)
                VALUES(100,1,"MARI ES MI AMOR","ROMANTICA","180 min"),
                (101,1,"COMO TE EXTRAÑO MI AMOR","ROMANTICA","160 min"),
                (102,1,"TE HE PROMETIDO","ROMANTICA","170 min"),
                (103,1,"PIDEME LA LUNA","ROMANTICA","170 min"),
                (200,2,"SIMPLEMENTE AMIGOS","BALADA","165 min"),
                (201,2,"QUIEN COMO TU","BALADA","175 min"),
                (300,3,"EL DESQUITE","REGETON","172 min"),
                (301,3,"EL REGRESA A MI","ROMANTICA","187 min"),
                (400,4,"EL BARCO","REGETON","163 min"),
                (401,4,"200 COPAS","REGETON","155 min"),
                (402,4,"CONTIGO VOY A MUERTE","REGETON","165 min")
                
            """.trimIndent()
            db?.execSQL(scriptIngresarCanciones)
        Log.i("bdd","Se han insertado los datos ")















    }

    fun crearAutor(
        idAutor: Int,
        nombre: String,
        pais: String,
        edad: String
    ): Boolean{
        val conexionEscritura = writableDatabase
        val valoresAGuardar = contentValuesOf()
        valoresAGuardar.put("idAutor", idAutor)
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("pais", pais)
        valoresAGuardar.put("edad", edad)

        val resultadoEscritura: Long = conexionEscritura
            .insert(
                "AUTORES",
                null,
                valoresAGuardar
            )
        conexionEscritura.close()
        return if(resultadoEscritura.toInt()==-1)false else true
    }
//    fun consultarUsuarios():ArrayList<BAutor>{
//        val scriptConsultaUsuario="SELECT*FROM AUTOR"
//        val baseDatosLectura= readableDatabase
//        //esto es como un inde del arreglo
//        val resultaConsultaLectura= baseDatosLectura.rawQuery(
//            scriptConsultaUsuario,
//            null
//        )
//        val existeUsuario= resultaConsultaLectura.moveToFirst()
//
//        val arregloUsuario= arrayListOf<BAutor>()
//        if(existeUsuario){
//            do{
//                val id=resultaConsultaLectura.getString(0)
//                val nombre=resultaConsultaLectura.getString(1)
//                val pais = resultaConsultaLectura.getString(2)
//                val edad = resultaConsultaLectura.getString(2)
//                if(id!=null){
//                    arregloUsuario.add(
//                        BAutor(id,nombre,pais, edad)
//                    )
//
//                }
//
//            }while (resultaConsultaLectura.moveToNext())
//
//        }
//        resultaConsultaLectura.close()
//        baseDatosLectura.close()
//        return arregloUsuario
//
//    }
fun consultarUsuarioPorId(id: Int): BAutor {

    val scriptConsultarUsuario = "SELECT * FROM AUTORES WHERE ID = ${id}"
//        val baseDatosLectura = this.readableDatabase
    val baseDatosLectura = readableDatabase
    val resultaConsultaLectura = baseDatosLectura.rawQuery(
        scriptConsultarUsuario,
        null
    )
    val existeUsuario = resultaConsultaLectura.moveToFirst()
    // val arregloUsuario = arrayListOf<EUsuarioBDD>()
    val usuarioEncontrado = BAutor(0, "", "","")
    if (existeUsuario) {
        do {
            val id = resultaConsultaLectura.getInt(0) // Columna indice 0 -> ID
            val nombre = resultaConsultaLectura.getString(1) // Columna indice 1 -> NOMBRE
            val pais =
                resultaConsultaLectura.getString(2) // Columna indice 2 -> DESCRIPCION
            val edad =
                resultaConsultaLectura.getString(3)
            if (id != null) {
                usuarioEncontrado.idAutor = id
                usuarioEncontrado.nombre = nombre
                usuarioEncontrado.pais = pais
                usuarioEncontrado.edad=edad
                // arregloUsuario.add(usuarioEncontrado)
            }
        } while (resultaConsultaLectura.moveToNext())
    }
    resultaConsultaLectura.close()
    baseDatosLectura.close()
    return usuarioEncontrado
}

    fun eliminarAutor(id: Int): Boolean{
        val conexionEscritura = writableDatabase
        val resultadoEliminacion = conexionEscritura
            .delete(
                "AUTORES",
                "idAutor=?",
                arrayOf(
                    id.toString()
                )
            )
        conexionEscritura.close()
        return if (resultadoEliminacion.toInt()==-1)false else true
    }



    fun actualizarAutor(
        nombre: String,
        pais: String,
        edad: String,
        idActualizar: Int
    ): Boolean{
        val conexionEscritura = writableDatabase
        val valoresActualizar= ContentValues()
        valoresActualizar.put("nombre", nombre)
        valoresActualizar.put("pais", pais)
        valoresActualizar.put("edad", edad)
        val resultadoActualizado= conexionEscritura
            .update(
                "AUTORES",
                valoresActualizar,
                "idAutor=?",
                arrayOf(
                    idActualizar.toString()
                )

            )
        conexionEscritura.close()
        return if(resultadoActualizado==-1)false else true
    }

    fun consultarAutores(): ArrayList<BAutor> {

        val scriptConsultarUsuario = "SELECT * FROM AUTORES"
        val baseDatosLectura = readableDatabase
        val resultaConsultaLectura = baseDatosLectura.rawQuery(scriptConsultarUsuario, null)
        val existeUsuario = resultaConsultaLectura.moveToFirst()
        var arregloUsuario = arrayListOf<BAutor>()

        if(existeUsuario){
            do{
                val id = resultaConsultaLectura.getInt(0) //columna con el indice 0 -> en nuestro caso es el identificador
                val nombre= resultaConsultaLectura.getString(1)
                val pais=resultaConsultaLectura.getString(2)
                val edad=resultaConsultaLectura.getString(3)
                if(id!=null){

                    arregloUsuario.add(
                        BAutor(id,nombre, pais, edad)
                    )
                }
            }while(resultaConsultaLectura.moveToNext())
        }

        resultaConsultaLectura.close()
        baseDatosLectura.close()
        return arregloUsuario
    }

    fun llenarlistaAutores(): ArrayList<BAutor>{
        var arregloUsuario = arrayListOf<BAutor>()
       val scriptMostrarAutores= "SELECT * FROM AUTORES"
        val baseDatosLectura= writableDatabase
        val resultadoMostrarAutores= baseDatosLectura.rawQuery(
            scriptMostrarAutores, null)
        if(resultadoMostrarAutores.moveToFirst()){
            do{
                arregloUsuario.add(
                    BAutor(resultadoMostrarAutores.getInt(0),
                        resultadoMostrarAutores.getString(1),
                        resultadoMostrarAutores.getString(2),
                        resultadoMostrarAutores.getString(3),


                        )
                )

            }while(resultadoMostrarAutores.moveToNext())
        }
        return arregloUsuario
    }

    ////////////*********************************///////////////////////

   fun crearCancion(
       idAutor: Int?,
       idCancion: Int,
       titulo: String,
       genero: String,
       duracion: String,
   ):Boolean{
       val conexionEscritura= writableDatabase
       val valoresAGuardar= ContentValues()
       valoresAGuardar.put("idAutor",idAutor)
       valoresAGuardar.put("idCancion", idCancion)
       valoresAGuardar.put("titulo",titulo)
       valoresAGuardar.put("genero",genero)
       valoresAGuardar.put("duracion",duracion)
       val resultadoEscritura: Long= conexionEscritura
           .insert(
               "CANCION",
               null,
               valoresAGuardar
           )
       conexionEscritura.close()
       return if(resultadoEscritura.toInt()==-1)false else true
   }


    fun llenarlistaCanciones(idAutor: Int): ArrayList<BCancion>{
        var arregloCancion = arrayListOf<BCancion>()
        val scriptMostrarCanciones= "SELECT * FROM CANCION WHERE idAutor=${idAutor}"
        val baseDatosLectura= writableDatabase
        val resultadoMostrarCanciones= baseDatosLectura.rawQuery(
            scriptMostrarCanciones, null)
        if(resultadoMostrarCanciones.moveToFirst()){
            do{
                arregloCancion.add(
                    BCancion(
                        resultadoMostrarCanciones.getInt(0),
                        resultadoMostrarCanciones.getInt(1),
                        resultadoMostrarCanciones.getString(2),
                        resultadoMostrarCanciones.getString(3),
                        resultadoMostrarCanciones.getString(4),
                        )
                )

            }while(resultadoMostrarCanciones.moveToNext())
        }
        return arregloCancion
    }

    fun consultarCancionesPorIdAutor(idAutor: Int):ArrayList<BCancion>{
        val arregloCancion = arrayListOf<BCancion>()
        val scriptConsultarCancion ="SELECT * FROM CANCION WHERE idAutor =${idAutor}"
        val baseDatosLectura = readableDatabase
        val resultadoConsultaLectura= baseDatosLectura.rawQuery(scriptConsultarCancion, null)
        val usuarioEncontrado = BCancion(0, 0,"", "","")
        if(resultadoConsultaLectura.moveToFirst()){
            do{

                val idCancion = resultadoConsultaLectura.getInt(1)
                val titulo= resultadoConsultaLectura.getString(2)
                val genero=resultadoConsultaLectura.getString(3)
                val duracion=resultadoConsultaLectura.getString(4)
                if(idCancion!=null){
                    arregloCancion.add(
                        BCancion(
                            usuarioEncontrado.idAutor,
                            usuarioEncontrado.idCancion,
                            usuarioEncontrado.titulo,
                            usuarioEncontrado.genero,
                            duracion
                        )
                    )
                }
            }while(resultadoConsultaLectura.moveToNext())
        }

        Log.i("bdd","Se ha consultado la cancion por el id del Usuario: ${idAutor}")
        //resultadoConsultaLectura.close()
        //baseDatosLectura.close()
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return  arregloCancion
    }

    fun consultarCanciones(): ArrayList<BCancion> {

        val scriptConsultarCancion = "SELECT * FROM CANCION"
        val baseDatosLectura = readableDatabase
        val resultaConsultaLectura = baseDatosLectura.rawQuery(scriptConsultarCancion, null)
        val existeCancion = resultaConsultaLectura.moveToFirst()
        var arregloCancion = arrayListOf<BCancion>()

        if(existeCancion){
            do{
                val idAutor= resultaConsultaLectura.getInt(0)
                val idCancion = resultaConsultaLectura.getInt(1)
                val titulo= resultaConsultaLectura.getString(2)
                val genero=resultaConsultaLectura.getString(3)
                val duracion=resultaConsultaLectura.getString(4)
                if(idCancion!=null){

                    arregloCancion.add(
                        BCancion(idAutor,idCancion,titulo, genero, duracion)
                    )
                }
            }while(resultaConsultaLectura.moveToNext())
        }

        resultaConsultaLectura.close()
        baseDatosLectura.close()
        return arregloCancion
    }

    fun actualizarCancion(
        titulo: String,
        genero: String,
        duracion: String,
        idActualizarC: Int
    ): Boolean{
        val conexionEscritura = writableDatabase
        val valoresActualizar= ContentValues()
        valoresActualizar.put("titulo",titulo)
        valoresActualizar.put("genero", genero)
        valoresActualizar.put("duracion", duracion)
        val resultadoActualizado= conexionEscritura
            .update(
                "CANCION",
                valoresActualizar,
                "idCancion=?",
                arrayOf(
                    idActualizarC.toString()
                )

            )
        conexionEscritura.close()
        return if(resultadoActualizado==-1)false else true
    }

    fun eliminarCancion(id: Int): Boolean{
        val conexionEscritura = writableDatabase
        val resultadoEliminacion = conexionEscritura
            .delete(
                "CANCION",
                "idCancion=?",
                arrayOf(
                    id.toString(),
                )
            )
        conexionEscritura.close()
        return if (resultadoEliminacion.toInt()==-1)false else true
    }



    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {

//     db!!.execSQL("DROP TABLE AUTOR")
//     onCreate(db)
//        Log.i("bdd","tabla borrada")

    }







}