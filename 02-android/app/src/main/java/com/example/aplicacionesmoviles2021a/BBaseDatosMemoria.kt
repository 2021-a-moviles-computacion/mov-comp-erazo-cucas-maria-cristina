package com.example.aplicacionesmoviles2021a

class BBaseDatosMemoria {
    companion object{
        val arregloBEntrenador = arrayListOf<BEntrenador>()
    }
    init{
        arregloBEntrenador
            .add(
                BEntrenador("Adrian","a@a.com")

            )
        arregloBEntrenador
            .add(
                BEntrenador("Joseph","b@b.com")
                )
        arregloBEntrenador
            .add(
                BEntrenador("Mary","c@c.com")
            )
    }
}