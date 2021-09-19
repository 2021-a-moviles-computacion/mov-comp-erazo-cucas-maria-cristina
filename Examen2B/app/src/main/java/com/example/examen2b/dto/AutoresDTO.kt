package com.example.examen2b.dto

class AutoresDTO(
    var edad: Int?= null,
    var nombre: String? = null,
    var pais: String?= null,

) {
    override fun toString(): String {
        if(nombre==null && pais==null && edad==null)
            return "error"

        else
            return "Autor: ${nombre}\nPais: ${pais}\nEdad: ${edad}"
    }
}