import java.util.*
import java.io.*
import kotlin.collections.ArrayList
import kotlin.system.exitProcess

fun main() {

    //Creacion de listas y carga inicial
    val listaLibros: ArrayList<Libro> = cargarLibros()
    val listaAutores: ArrayList<Autor> = cargarAutores()
    //val listaReparto: ArrayList<Reparto> =
    //    cargarRepartos(listaLibros, listaAutores)

    var seleccion:Int


    println("Bienvenid@ al sistema de registro de series")
    do {
        println("Seleccione una opción: ")
        println(
            "1- Ingresar una nueva serie, actor o reparto (Create)\n" +
                    "2- Mostrar series, actores o reparto (Read)\n" +
                    "3- Actualizar una serie o actor (Update)\n" +
                    "4- Eliminar una serie o actor (Delete)\n" +
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
                  //  guardarRepartos(listaReparto)
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
                "****************\n" +
                        "Registro exitoso\n" +
                        "****************"
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
                "*********************\n" +
                        "Actualización exitosa\n" +
                        "*********************"
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
    println("Presione cualquier tecla para continuar")
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
                "*****************************************************************************\n" +
                        "La opcion ingresada no es correcta, revise si ingresó un caracter no numérico\n" +
                        "*****************************************************************************"
            )
        }
        2 -> {
            println(
                "**********************\n" +
                        "Elemento no encontrado\n" +
                        "**********************"
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
    println("Presione cualquier tecla para continuar")
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
    idActor: Int
): Autor? {
    var autorRespuesta: Autor? = null
    listaAutores.forEach { actor ->
        if (actor.idAutor == idActor) {
            autorRespuesta = actor
        }
    }
    return autorRespuesta
}
