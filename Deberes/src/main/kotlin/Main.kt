import kotlin.collections.ArrayList
import kotlin.system.exitProcess

fun main() {

    //Creacion de listas y carga inicial
    val listaLibros: ArrayList<Libro> = cargarLibros()
    val listaAutores: ArrayList<Autor> = cargarAutores()


    var seleccion:Int


    println("LIBROS Y AUTORES")
    do {
        println("Seleccione una opción: ")
        println(
            "1- Ingresar una nuevo libro, o autor\n" +
                    "2- Mostrar libros o autores\n" +
                    "3- Actualizar un libro o autor \n" +
                    "4- Eliminar una libro o autor \n" +
                    "Ingrese 5 si desea salir del programa.\n"

        )
        try {
            seleccion = readLine()?.toInt() as Int
            when (seleccion) {
                1 -> {
                    crear(listaLibros, listaAutores)
                }
                2 -> {
                    leer(listaLibros, listaAutores)
                }
                3 -> {
                    actualizar(listaLibros, listaAutores)
                }
                4 -> {
                    borrar(listaLibros, listaAutores)
                }
                5 -> {
                    guardarLibros(listaLibros)
                    guardarAutores(listaAutores)

                    exitProcess(0)
                }
                else -> imprimirError(0)
            }

        } catch (e2: Exception) {
            imprimirError(1)
        }

    } while (true)
}






fun imprimirExito(opcion: Int = 0) {
    when (opcion) {
        0 -> {
            println(
                "###################\n" +
                        "Se ha guardado exitosamente\n" +
                        "###################"
            )
        }
        1 -> {
            println(
                "****************\n" +
                        "Consulta exitosa\n" +
                        "****************"
            )
        }
        2 -> {
            println(
                "###################\n" +
                        "Actualización exitosa\n" +
                        "###################"
            )
        }
        else -> {
            println(
                "###################\n" +
                        "Error desconocido\n" +
                        "###################"
            )
        }
    }

    readLine()
}

fun imprimirError(opcion: Int = 0) {
    when (opcion) {
        0 -> {
            println(
                "********************\n" +
                        "Opción no disponible\n" +
                        "********************"
            )
        }
        1 -> {
            println(
                "#######################################################################################\n" +
                        "La opcion ingresada no es correcta, revise si ingresó un caracter no numérico\n" +
                        "################################################################################"
            )
        }
        2 -> {
            println(
                "###################\n" +
                        "Elemento no encontrado\n" +
                        "###################"
            )
        }
        3 -> {
            println(
                "*******************\n" +
                        "Operación cancelada\n" +
                        "*******************"
            )
        }
        else -> {
            println(
                "*****************\n" +
                        "Error desconocido\n" +
                        "*****************"
            )
        }
    }

    readLine()
}


fun buscarLibroID(
    listaLibros: ArrayList<Libro>,
    idLibro: String
): Libro? {
    var libroRespuesta: Libro? = null
    listaLibros.forEach { serie ->
        if (serie.idLibro.equals(idLibro)) {
            libroRespuesta = serie
        }
    }
    return libroRespuesta
}

fun buscarAutorID(
    listaAutores: ArrayList<Autor>,
    idAutor: Int
): Autor? {
    var autorRespuesta: Autor? = null
    listaAutores.forEach { actor ->
        if (actor.idAutor == idAutor) {
            autorRespuesta = actor
        }
    }
    return autorRespuesta
}
