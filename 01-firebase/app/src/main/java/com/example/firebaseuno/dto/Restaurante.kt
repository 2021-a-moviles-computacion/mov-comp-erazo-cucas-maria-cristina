package com.example.firebaseuno.dto

class Restaurante (
    val nombre: String? = null
        ) {
    override fun toString(): String {
        return "${ nombre}"
    }
}