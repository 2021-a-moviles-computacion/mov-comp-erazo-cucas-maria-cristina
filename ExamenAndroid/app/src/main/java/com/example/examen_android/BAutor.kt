package com.example.examen_android

import android.os.Parcel
import android.os.Parcelable

class BAutor(

    var idAutor: Int,
    var nombre: String?,
    var pais: String?,
    var edad: String?,

    ):Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun toString(): String{
        return "${idAutor}--${nombre} ---- ${pais}----${edad}"
    }

    override fun describeContents(): Int {
      return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idAutor)
      parcel.writeString(nombre)
      parcel.writeString(pais)
      parcel.writeString(edad)
    }

    companion object CREATOR : Parcelable.Creator<BAutor> {
        override fun createFromParcel(parcel: Parcel): BAutor {
            return BAutor(parcel)
        }

        override fun newArray(size: Int): Array<BAutor?> {
            return arrayOfNulls(size)
        }
    }

}