package com.example.examen_android

import android.os.Parcel
import android.os.Parcelable

class BCancion(
    var idAutor: Int?,
    var idCancion: Int,
    var titulo: String?,
    var genero: String?,
    var duracion: String?,
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }
    override fun toString(): String{
        return "${idAutor}--${idCancion} ---- ${titulo}----${genero}---${duracion}"
    }
    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(idAutor)
        parcel.writeInt(idCancion)
        parcel.writeString(titulo)
        parcel.writeString(genero)
        parcel.writeString(duracion)


    }

    companion object CREATOR : Parcelable.Creator<BCancion> {
        override fun createFromParcel(parcel: Parcel): BCancion {
            return BCancion(parcel)
        }

        override fun newArray(size: Int): Array<BCancion?> {
            return arrayOfNulls(size)
        }
    }
}