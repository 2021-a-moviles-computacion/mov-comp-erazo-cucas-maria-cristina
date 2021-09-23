package com.example.examen2b.dto

import android.os.Parcel
import android.os.Parcelable

class AutorDTO (
    var uid:String?,
    var nombre:String?,
    var pais:String?,
    var edad:String?,

):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }
    override fun toString(): String {
        return  "Nombre: ${nombre} \n" +
                "Pais: ${pais} \n" +
                "Edad: ${edad} años\n"

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uid)
        parcel.writeString(nombre)
        parcel.writeString(pais)
        parcel.writeString(edad)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AutorDTO> {
        override fun createFromParcel(parcel: Parcel): AutorDTO {
            return AutorDTO(parcel)
        }

        override fun newArray(size: Int): Array<AutorDTO?> {
            return arrayOfNulls(size)
        }
    }

}