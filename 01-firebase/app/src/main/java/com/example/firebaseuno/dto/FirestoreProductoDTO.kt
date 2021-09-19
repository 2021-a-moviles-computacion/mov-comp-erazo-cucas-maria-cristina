package com.example.firebaseuno.dto

class FirestoreProductoDTO(

    var nombre:String?,
    var precio: Double?

) {

    override fun toString(): String{
        return "${nombre}" // + " ${precio}"
    }
}