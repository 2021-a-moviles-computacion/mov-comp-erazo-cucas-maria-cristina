package com.example.deber02_skype

import android.os.Parcel
import android.os.Parcelable
import java.util.*

class DLiga(
    val nombre: String?,

    val fecha: String?,
    val imagen: Int,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),

        parcel.readString(),
        parcel.readInt()

    ) {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<DLiga> {
        override fun createFromParcel(parcel: Parcel): DLiga {
            return DLiga(parcel)
        }

        override fun newArray(size: Int): Array<DLiga?> {
            return arrayOfNulls(size)
        }
    }
}