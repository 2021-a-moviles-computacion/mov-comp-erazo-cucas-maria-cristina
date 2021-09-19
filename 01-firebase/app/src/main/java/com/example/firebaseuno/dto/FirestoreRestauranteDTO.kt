package com.example.firebaseuno.dto

data class FirestoreRestauranteDTO (
    var nombre:String?
    ) {

    init {
        this.nombre = null
    }
        override fun toString():String{
            return "${nombre}"
        }
}