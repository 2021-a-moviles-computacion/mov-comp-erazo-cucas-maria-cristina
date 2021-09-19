package com.example.examen2b.dto

class CancionesDTO(
    var tituloCancion: String?= null,
    var generoCancion: String? = null,
    var duracionCancion: String?= null,

    ) {
    override fun toString(): String {
        if(tituloCancion==null && generoCancion==null && duracionCancion==null)
            return "error"

        else
            return "Titulo: ${tituloCancion}\nGenero: ${generoCancion}\nDuracion: ${duracionCancion}"
    }
}