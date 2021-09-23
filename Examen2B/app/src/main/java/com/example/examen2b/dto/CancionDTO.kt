package com.example.examen2b.dto

import android.os.Parcel
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng

class CancionDTO (
    var uid:String?,
    var uid_usuario:String?,
    var ubicacion: LatLng?,
    var tituloCancion: String?,
    var generoCancion: String?,
    var duracionCancion: String?

    ):Parcelable
{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(LatLng::class.java.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }
    override fun toString(): String {
        return  "Ubicacion: ${ubicacion} \n" +
                "Titulo: ${tituloCancion} \n" +
                "Genero: ${generoCancion} \n" +
                "Duracion: ${duracionCancion} \n"

    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uid)
        parcel.writeString(uid_usuario)
        parcel.writeParcelable(ubicacion, flags)
        parcel.writeString(tituloCancion)
        parcel.writeString(generoCancion)
        parcel.writeString(duracionCancion)
    }

    companion object CREATOR : Parcelable.Creator<CancionDTO> {
        override fun createFromParcel(parcel: Parcel): CancionDTO {
            return CancionDTO(parcel)
        }

        override fun newArray(size: Int): Array<CancionDTO?> {
            return arrayOfNulls(size)
        }
    }
}