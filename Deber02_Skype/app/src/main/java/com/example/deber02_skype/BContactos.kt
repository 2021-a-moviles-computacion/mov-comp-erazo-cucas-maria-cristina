package com.example.deber02_skype

import android.os.Parcel
import android.os.Parcelable
import java.text.SimpleDateFormat
import java.util.*

class BContactos(
    val nombre: String?,
    val fecha: String?,
    val imagen: Int,
    val liga: DLiga? =null,
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),

        parcel.readString(),
        parcel.readInt(),
        parcel.readParcelable(DLiga::class.java.classLoader)
    ) {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(parcel: Parcel?, flag: Int) {
        parcel?.writeString(nombre)

        parcel?.writeString(fecha)
        parcel?.writeInt(imagen)
        parcel?.writeParcelable(liga,flag)


    }

    companion object CREATOR : Parcelable.Creator<BContactos> {
        override fun createFromParcel(parcel: Parcel): BContactos {
            return BContactos(parcel)
        }

        override fun newArray(size: Int): Array<BContactos?> {
            return arrayOfNulls(size)
        }
    }

}
