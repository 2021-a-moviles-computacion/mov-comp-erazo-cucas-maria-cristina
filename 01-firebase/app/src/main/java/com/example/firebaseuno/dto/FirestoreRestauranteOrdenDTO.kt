package com.example.firebaseuno.dto

class FirestoreRestauranteOrdenDTO(
    val nombre: String?,
    val precio: Double?,
    val cantidad: Int?,
    val totalOrden: Double?

){
    override fun toString(): String {

        return "Producto: ${nombre}\n" +
                "Precio Unit.: $${precio}\nCantidad: ${cantidad}"

    }
}

