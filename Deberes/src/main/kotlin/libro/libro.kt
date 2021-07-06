import java.io.*
import java.util.*
import kotlin.collections.ArrayList

fun cargarLibros(): ArrayList<Libro> {
    val archivoLibros: File?
    var frLibros: FileReader? = null
    val brLibros: BufferedReader?
    val listaLibros: ArrayList<Libro> = ArrayList()




    try {

        archivoLibros = File("libros.csv")
        frLibros = FileReader(archivoLibros)
        brLibros = BufferedReader(frLibros)

        var linea: String?
        while (brLibros.readLine().also { linea = it } != null) {
            var auxLin = 0
            var auxFecha = 0

            var diaLibro = 0
            var anioLibro = 0
            var mesLibro = 0

            var libroID = ""
            var titulo = ""
            var tipo = ' '
            var publicacion = Date(2000, 1, 1)
            var vendido = true


            val tokens = StringTokenizer(linea, ",")

            while (tokens.hasMoreTokens()) {
                when (auxLin) {
                    0 -> {
                        libroID = tokens.nextToken()
                    }
                    1 -> {
                        titulo = tokens.nextToken()
                    }
                    2 -> {
                        tipo = tokens.nextToken().toCharArray()[0]
                    }
                    3 -> {
                        val tokFecha = StringTokenizer(tokens.nextToken(), "/")
                        while (tokFecha.hasMoreTokens()) {
                            when (auxFecha) {
                                0 -> {

                                    anioLibro = tokFecha.nextToken().toInt()
                                }
                                1 -> {
                                    mesLibro = tokFecha.nextToken().toInt()
                                }
                                2 -> {
                                    diaLibro = tokFecha.nextToken().toInt()
                                }
                            }
                            auxFecha++
                        }
                        publicacion = Date(anioLibro, mesLibro, diaLibro)
                    }
                    4 -> {
                        val a = tokens.nextToken()
                        if (a == "true") {
                            vendido = true
                        } else if (a == "false") {
                            vendido = false
                        }

                    }
                }
                auxLin++
            }

            listaLibros.add(
                Libro(
                    libroID,
                    titulo,
                    tipo,
                    publicacion,
                    vendido
                )
            )


        }
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        try {
            frLibros?.close()
        } catch (e2: Exception) {
            e2.printStackTrace()
        }
    }
    return listaLibros
}


fun registrarLibro(): Libro? {
    var idLibro: String? = ""
    var tituloLibro: String? = ""
    var tipoLibro: Char? = null
    val fechaPublicacionLibro: String?

    var flag = true
    var fechaAux = 0


    var diaLibro = 0
    var mesLibro = 0
    var anioLibro = 0
    try {
        println("Ingrese el id del libro")
        idLibro = readLine() as String
        println("Ingrese el nombre del libro ")
        tituloLibro = readLine() as String
        println("Ingrese el tipo de libro con la letra inicial por ejemplo ROMANCE R")
        tipoLibro = readLine()?.toCharArray()?.get(0)
        println("Ingrese la fecha de publicacion del libro aaaa/mm/dd")
        fechaPublicacionLibro = readLine()
        println("Por defecto el libro registrada estará como \"VENDIDO\"")


        val tokFecha = StringTokenizer(fechaPublicacionLibro, "/")
        while (tokFecha.hasMoreTokens()) {
            when (fechaAux) {
                0 -> {
                    anioLibro = tokFecha.nextToken().toInt()
                }
                1 -> {
                    mesLibro = tokFecha.nextToken().toInt()
                }
                2 -> {
                    diaLibro = tokFecha.nextToken().toInt()
                }
            }
            fechaAux++
        }


    } catch (eRead1: NumberFormatException) {
        imprimirError(1)
        flag = false
    }
    if (flag) {
        return Libro(
            idLibro,
            tituloLibro,
            tipoLibro,
            Date(anioLibro, mesLibro, diaLibro),
            true
        )
    }
    return null
}


fun imprimirLibros(
    listaLibros: ArrayList<Libro>
) {

    listaLibros.forEach { libro ->
        println("Valor: $libro")
    }
}

fun actualizarLibros(

    libro: Libro?
): Libro? {
    var nombreLibro: String? = ""
    var tipoLibro: Char? = null
    val fechaPublicacionLibro: String?

    val alAireLibro: String?
    var alAire = false

    var flag = true
    var fechaAux = 0

    var diaLibro = 0
    var mesLibro = 0
    var anioLibro = 0
    try {
        println("Ingrese el nuevo nombre del libro ")
        nombreLibro = readLine() as String
        println("Ingrese el tipo de libro por ejemplo R  de romance")
        tipoLibro = readLine()?.toCharArray()?.get(0)
        println("Ingrese la fecha de publicacion del libro aaaa/mm/dd")
        fechaPublicacionLibro = readLine()

        val tokFecha = StringTokenizer(fechaPublicacionLibro, "/")
        while (tokFecha.hasMoreTokens()) {
            when (fechaAux) {
                0 -> {
                    anioLibro = tokFecha.nextToken().toInt()
                }
                1 -> {
                    mesLibro = tokFecha.nextToken().toInt()
                }
                2 -> {
                    diaLibro = tokFecha.nextToken().toInt()
                }
            }
            fechaAux++
        }

        println(
            "¿Desea cambiar el estado de la serie?\n" +
                    "Estado actual: ${libro?.vendido}\n" +
                    "Ingrese 1 si desea Activarlo\n" +
                    "Ingrese 0 si desea Desactivarlo"
        )
        alAireLibro = readLine()
        if (alAireLibro.equals("1")) {
            alAire = true
        }

    } catch (eRead1: NumberFormatException) {
        imprimirError(1)
        flag = false
    }
    if (flag) {
        return Libro(
            libro?.idLibro,
            nombreLibro,
            tipoLibro,
            Date(anioLibro, mesLibro, diaLibro),
            alAire
        )
    }
    return null
}

fun guardarLibros(
    listaLibros: ArrayList<Libro>
) {
    var stringLibros = ""
    listaLibros.forEach { libro ->
        stringLibros = stringLibros + "" +
                "${libro.idLibro}," +
                "${libro.tituloLibro}," +
                "${libro.tipoLibro}," +
                "${libro.fechaPublicacionLibro?.year}/${libro.fechaPublicacionLibro?.month}/${libro.fechaPublicacionLibro?.date}," +
                "${libro.vendido}\n"
    }
    var flwriter: FileWriter? = null
    try {
        flwriter =
            FileWriter("libros.csv")
        val bfwriter = BufferedWriter(flwriter)

        bfwriter.write(stringLibros)
        bfwriter.close()
        println("Archivo de libros actualizado")
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        if (flwriter != null) {
            try {
                flwriter.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}

class Libro(
    val idLibro: String?,
    val tituloLibro: String?,
    val tipoLibro: Char?,
    val fechaPublicacionLibro: Date?,
    val vendido: Boolean?

) {

    override fun toString(): String {
        return "\n\tId: \t\t$idLibro\n" +
                "\tNombre: \t$tituloLibro\n" +
                "\tClasificación: \t$tipoLibro\n" +
                "\tFecha de Inicio:  ( ${fechaPublicacionLibro?.year} / ${fechaPublicacionLibro?.month} / ${fechaPublicacionLibro?.date} )\n" +
                "\tVendido: \t$vendido\n"
    }
}
