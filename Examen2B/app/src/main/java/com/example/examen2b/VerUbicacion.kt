package com.example.examen2b

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.examen2b.dto.CancionDTO
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class VerUbicacion : AppCompatActivity() {
    private lateinit var mapa: GoogleMap
    var permisos = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_ubicacion)

        var cancionIntent = intent.getParcelableExtra<CancionDTO>("canciones")
        Log.i("verUbicacion","ubicacion: ${cancionIntent!!.ubicacion?.javaClass}")

        solicitarPermisos()
        val fragmentMapa = supportFragmentManager.findFragmentById(R.id.fr_VerMapa) as SupportMapFragment
        fragmentMapa.getMapAsync { googleMap ->
            if (googleMap != null) {
                //mapa.clear()
                mapa = googleMap
                establecerConfiguracionMapa()

                val ubcacionCanciones = cancionIntent!!.ubicacion
                val titulo = "Ubicacion"
                val zoom = 17f

                anadirMarcador(ubcacionCanciones!!, titulo)
                moverCamaraZoom(ubcacionCanciones!!, zoom)

            }
        }

    }
    fun anadirMarcador(latLng: LatLng, title: String){
        mapa.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(title)
        )
    }

    fun moverCamaraZoom(latLng: LatLng, zoom: Float ){
        mapa.moveCamera(
            CameraUpdateFactory
                .newLatLngZoom(latLng, zoom)
        )

    }

    fun solicitarPermisos(){

        val contexto = this. applicationContext
        val permisosFineLocation = ContextCompat
            .checkSelfPermission(
                contexto,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
        val tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED
        if(tienePermisos){
            permisos = true
        }else{
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ),
                1
            )
        }
    }

    fun establecerConfiguracionMapa(){
        val contexto = this. applicationContext
        with(mapa){
            val permisosFineLocation = ContextCompat
                .checkSelfPermission(
                    contexto,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )

            val tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED
            if(tienePermisos){
                mapa.isMyLocationEnabled = true //no tenemos aun permisos
            }
            uiSettings.isZoomControlsEnabled = true
            uiSettings.isMyLocationButtonEnabled = true //no tenemos aun permisos
        }

    }
}