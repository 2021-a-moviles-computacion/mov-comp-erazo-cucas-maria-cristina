fun crear(
    listaLibros: ArrayList<Libro>,
    listaAutores: ArrayList<Autor>

){
    println(
        "Desea Ingresar: " +
                "\n1- Libros" +
                "\n2- Autores" +
                "\n3- Reparto"
    )
    try {
        when (readLine()?.toInt() as Int) {
            1 -> {
                opcionC1(listaLibros)
            }
            2 -> {
                opcionC2(listaAutores)
            }

            else -> {
                imprimirError(0)
            }
        }
    } catch (err: Exception) {
        imprimirError(1)
    }

}

fun opcionC1(
    listaLibros: ArrayList<Libro>
){
    val libroAux: Libro? = registrarLibro()
    if (libroAux != null) {
        listaLibros.add(libroAux)
        imprimirExito(0)
    }
}

fun opcionC2(
    listaAutores: ArrayList<Autor>
){
    val autorAux: Autor? = registrarAutor()
    if (autorAux != null) {
        listaAutores.add(autorAux)
        imprimirExito(0)
    }
}








//////////********BORRAR***************************////////////////////


fun borrar(
    listaLibros: ArrayList<Libro>,
    listaAutores: ArrayList<Autor>
){
    println(
        "Desea Eliminar: " +
                "\n1- Libros" +
                "\n2- Autores"
    )
    try {
        when (readLine()?.toInt() as Int) {
            1 -> {
                opcionD1(listaLibros, listaAutores)
            }
            2 -> {
                opcionD2(listaAutores)
            }
            else -> imprimirError(0)
        }
    } catch (err: Exception) {
        imprimirError(1)
    }
}

fun opcionD1(
    listaLibros: ArrayList<Libro>,
    listaAutores: ArrayList<Autor>
){
    println("Ingrese el id del libro a eliminar:\n")
    try {
        val ingreso = readLine() as String
        val libroID: Libro? = buscarLibroID(listaLibros, ingreso)
        val confirmacion: String?
        if (libroID != null) {
            println("Informacion del libro a eliminar:")
            println(libroID)
            try {
                println(
                    "¿Está seguro de eliminar la serie?\n" +
                            "Ingrese 1 si está seguro\n" +
                            "Ingrese 0 si no desea eliminarla"
                )
                confirmacion = readLine()
                if (confirmacion.equals("1")) {
//                    listaAutores.removeIf { autores ->
//                        autores.libro.idLibro.equals(ingreso)
//                    }
                    listaLibros.removeIf { libro ->
                        (libro.idLibro.equals(ingreso))
                    }
                } else {
                    imprimirError(3)
                }
            } catch (err: Exception) {
                imprimirError(1)
            }
        } else {
            imprimirError(2)
        }
    } catch (err: Exception) {
        imprimirError(1)
    }
}

fun opcionD2(
    listaAutores: ArrayList<Autor>,
   // listaReparto: ArrayList<Reparto>
){
    println("Ingrese el id del autor que desea eliminar\n")
    imprimirAutores(listaAutores)
    try {
        val entrada = readLine()?.toInt() as Int
        val actorID: Autor? = buscarAutorID(listaAutores, entrada)
        val seguro: String?
        if (actorID != null) {
            println("Información actual del actor:")
            println(actorID)
            try {
                println(
                    "¿Está seguro de eliminar el actor?\n" +
                            "Ingrese 1 si está seguro\n" +
                            "Ingrese 0 si no desea eliminarlo"
                )
                seguro = readLine()
                if (seguro.equals("1")) {
//                    listaAutores.removeIf { reparto ->
//                        reparto.autor.idAutor == entrada
//                    }
                    listaAutores.removeIf { autor ->
                        (autor.idAutor == entrada)
                    }
                } else {
                    imprimirError(3)
                }
            } catch (err: Exception) {
                imprimirError(1)
            }
        } else {
            imprimirError(2)
        }
    } catch (err: Exception) {
        imprimirError(1)
    }
}



////////*************************UPDATE*******************


fun actualizar(
    listaLibros: ArrayList<Libro>,
    listaAutores: ArrayList<Autor>
){
    println(
        "Desea actualizar: " +
                "\n1- Libros" +
                "\n2- Autores"
    )
    try {

        when (readLine()?.toInt() as Int) {
            1 -> {
                opcionU1(listaLibros)
            }
            2 -> {
                opcionU2(listaAutores)
            }
            else -> {
                imprimirError(0)
            }
        }
    } catch (err: Exception) {
        imprimirError(1)
    }
}

fun opcionU1(
    listaLibros: ArrayList<Libro>,
  //  listaAutores: ArrayList<Autor>
){
    println("Ingrese el id del libro a actualizar:\n")

    try {

        val entrada = readLine() as String
        val libroID: Libro? = buscarLibroID(listaLibros, entrada)
        val updateLibro: Libro?
        if (libroID != null) {
            println("Información actual de la libro:")
            println(libroID)
            updateLibro = actualizarLibros(libroID)

            listaLibros.removeIf { libro ->
                (libro.idLibro.equals(entrada))
            }
            if (updateLibro != null) {
                var libroAux: Libro? = null
                listaLibros.add(updateLibro)
                listaLibros.forEach { libro ->
                    if (libro.idLibro.equals(updateLibro.idLibro)) {
                        libroAux = libro
                    }
                }
                listaLibros.removeIf { reparto ->
                    reparto.idLibro.equals(updateLibro.idLibro)
                }
                if (libroAux != null) {
//                    listaLibros.add(
//                        Libro(
//                          //  updateLibro,
//                        //    libroAux!!.tituloLibro,
//                           // libroAux!!.fechaIngreso,
//                            //libroAux!!.comentario
//                        )
//                    )
                    println(
                        "Nueva información:\n" +
                                "+${updateLibro}"
                    )
                    imprimirExito(2)
                }
            }
        } else {
            imprimirError(2)
        }
    } catch (err: Exception) {
        imprimirError(1)
    }
}

fun opcionU2(
    listaAutores: ArrayList<Autor>,
  //  listaReparto: ArrayList<Reparto>
){
    println("Ingrese el id del autor que desea actualizar\n")
    try {

        val autor = readLine()?.toInt() as Int
        val autorID: Autor? = buscarAutorID(listaAutores, autor)
        val updateAutor: Autor?
        if (autorID != null) {
            println("Información actual del autor:")
            println(autorID)
            updateAutor = actualizarAutor(autorID)
            listaAutores.removeIf { actor1 ->
                (actor1.idAutor == autor)
            }
            if (updateAutor != null) {
                var repartoAux: Autor? = null
                listaAutores.add(updateAutor)

                listaAutores.forEach { reparto ->
                    if (reparto.idAutor == updateAutor.idAutor) {
                        repartoAux = reparto
                    }
                }
                listaAutores.removeIf { reparto ->
                    reparto.idAutor == updateAutor.idAutor
                }
//                listaAutores.add(
//                   Autor(
//                        repartoAux!!.paisAutor,
//                        updateAutor
//
//                        updateAutor,
//                        repartoAux!!.edadActor,
//                        repartoAux!!.comentario
//                    )
//                )
                //---------------------------------
                println(
                    "Nueva información:\n" +
                            "+${updateAutor}"
                )
            }
        } else {
            imprimirError(2)
        }
    } catch (err: Exception) {
        imprimirError(1)
    }
}

//



////////////////*****************************buscar********

fun leer(
    listaLibros: ArrayList<Libro>,
    listaAutores: ArrayList<Autor>
){
    println(
        "¿Qué desea ver?: " +
                "\n1- Libros" +
                "\n2- Autores"

    )
    try {

        when (readLine()?.toInt() as Int) {
            1 -> {
                opcionR1(listaLibros)
            }
            2 -> {
                opcionR2(listaAutores)
            }

            else -> {
                imprimirError(0)
            }
        }
    } catch (err: Exception) {
        imprimirError(1)
    }
}

//fun imprimirLibroReparto(
  //  listaRepartos: ArrayList<Reparto>,
  //  idLibro: String?
//) {
 //   listaRepartos.forEach { reparto ->
   //     if (reparto.serie.idSerie.equals(idLibro)) {
     //       println("Valor: $reparto")
      //  }

    //}
//}

fun opcionR1(
    listaLibros: ArrayList<Libro>
){
    println("Lista Series")
    imprimirLibros(listaLibros)
    imprimirExito(1)
}

fun opcionR2(
    listaAutores: ArrayList<Autor>
){
    println("Lista Autores")
    imprimirAutores(listaAutores)
    imprimirExito(1)
}

