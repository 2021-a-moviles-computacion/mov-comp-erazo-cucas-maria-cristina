package com.example.examen2b

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.examen2b.dto.AutorDTO
import com.example.examen2b.dto.CancionDTO
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EditarCancion : AppCompatActivity()  {
    var cancion: CancionDTO?=null
    val CODIGO_RESPUESTA_INTENT_EXPLICITO = 401
    var ubicacionMapa: LatLng? = null
    private lateinit var mapa: GoogleMap
    var permisos = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_cancion)
        val fragmentMapa = supportFragmentManager.findFragmentById(R.id.fr_EditarCancionMapa1) as SupportMapFragment
        fragmentMapa?.view?.visibility = View.INVISIBLE

        solicitarPermisos()

        cancion = intent.getParcelableExtra<CancionDTO>("cancion")


        val ingresarDireccionCancion = findViewById<EditText>(R.id.ti_editar_direccionCancion)
        val titulo = findViewById<EditText>(R.id.editar_titulo)
        val genero = findViewById<EditText>(R.id.editar_genero)
        val duracion = findViewById<EditText>(R.id.editar_duracion)


        val ingresarLatitud = findViewById<EditText>(R.id.ti_latitud_actualizar)
        val ingresarLongitud = findViewById<EditText>(R.id.ti_longitud_actualizar)



        ingresarDireccionCancion.setText("${cancion!!.ubicacion!!.latitude.toString()} ; ${cancion!!.ubicacion!!.longitude.toString()}")
        titulo.setText(cancion!!.tituloCancion.toString())
        genero.setText(cancion!!.generoCancion.toString())
        duracion.setText(cancion!!.duracionCancion.toString())

        ingresarLatitud.setText(cancion!!.ubicacion!!.latitude.toString())
        ingresarLongitud.setText(cancion!!.ubicacion!!.longitude.toString())



        ingresarDireccionCancion.addTextChangedListener(crearusuarioTextWatcher)
        titulo.addTextChangedListener(crearusuarioTextWatcher)
        genero.addTextChangedListener(crearusuarioTextWatcher)
        duracion.addTextChangedListener(crearusuarioTextWatcher)



        val botoneditarCancion=findViewById<Button>(R.id.btn_editarCancion)

        botoneditarCancion
            .setOnClickListener {
                actualizarCancion()
            }

        val botonActualizarUbicacion = findViewById<Button>(R.id.btn_ir_actualizarDireccionMapa)
        botonActualizarUbicacion
            .setOnClickListener {
                mapa()
            }
    }

    fun mapa(){

        val ingresarDireccionCancion = findViewById<EditText>(R.id.ti_editar_direccionCancion)


        val ingresarLatitud = findViewById<EditText>(R.id.ti_latitud_actualizar)
        val ingresarLongitud = findViewById<EditText>(R.id.ti_longitud_actualizar)

        visibilidad(true)
        val fragmentMapa = supportFragmentManager.findFragmentById(R.id.fr_EditarCancionMapa1) as SupportMapFragment
        fragmentMapa.getMapAsync{ googleMap ->
            if(googleMap!=null){
                mapa = googleMap
                establecerConfiguracionMapa()

                val ubcacionCancion = cancion!!.ubicacion
                val titulo = "Ubicacion"
                val zoom = 17f

                anadirMarcador(ubcacionCancion!!, titulo)
                moverCamaraZoom(ubcacionCancion!!, zoom)
            }
        }

        val botonBuscarEnMapa = findViewById<Button>(R.id.bnt_BuscarEnMapaActualizar)
        botonBuscarEnMapa
            .setOnClickListener {
                //mapa.clear()
                val buscar = LatLng(ingresarLatitud.text.toString().toDouble(), ingresarLongitud.text.toString().toDouble())
                val zoom = 17f
                moverCamaraZoom(buscar,zoom)
                anadirMarcador(buscar,"Direccion")


            }


        val botonGuardarUbicacion = findViewById<Button>(R.id.btn_guardarActualizarUbicacion)
        botonGuardarUbicacion
            .setOnClickListener {
                visibilidad(false)
                ingresarDireccionCancion.setText("${ingresarLatitud.text.toString()} ; ${ingresarLongitud.text.toString()}")
                ubicacionMapa = LatLng(ingresarLatitud.text.toString().toDouble(), ingresarLongitud.text.toString().toDouble())

            }
    }

    fun visibilidad(mapa:Boolean){

        val ingresarDireccionCancion = findViewById<EditText>(R.id.ti_editar_direccionCancion)
        val titulo = findViewById<EditText>(R.id.editar_titulo)
        val genero = findViewById<EditText>(R.id.editar_genero)
        val duracion = findViewById<EditText>(R.id.editar_duracion)


        val botonCrearUbicacion = findViewById<Button>(R.id.btn_ir_actualizarDireccionMapa)
        val botonCrearCancion = findViewById<Button>(R.id.btn_editarCancion)
        val fragmentMapa = supportFragmentManager.findFragmentById(R.id.fr_EditarCancionMapa1) as SupportMapFragment
        val botonGuardarUbicacion = findViewById<Button>(R.id.btn_guardarActualizarUbicacion)

        val ingresarLatitud = findViewById<EditText>(R.id.ti_latitud_actualizar)
        val ingresarLongitud = findViewById<EditText>(R.id.ti_longitud_actualizar)
        val botonBuscarEnMapa = findViewById<Button>(R.id.bnt_BuscarEnMapaActualizar)



        val tv12 = findViewById<TextView>(R.id.textView12)
        val tv13 = findViewById<TextView>(R.id.textView13)
        val tv14 = findViewById<TextView>(R.id.textView14)
        val tv15 = findViewById<TextView>(R.id.textView15)



        if(mapa){

            ingresarDireccionCancion.isVisible = false
            titulo.isVisible = false
            genero.isVisible = false
            duracion.isVisible = false

            botonCrearUbicacion.isVisible = false
            botonCrearCancion.isVisible = false

            tv12.isVisible = false
            tv13.isVisible = false
            tv14.isVisible = false
            tv15.isVisible = false


            fragmentMapa?.view?.visibility = View.VISIBLE
            botonGuardarUbicacion.isVisible = true
            ingresarLatitud.isVisible = true
            ingresarLongitud.isVisible = true
            botonBuscarEnMapa.isVisible = true
        }else{

            ingresarDireccionCancion.isVisible = true
            titulo.isVisible = true
            genero.isVisible = true
            duracion.isVisible = true

            botonCrearUbicacion.isVisible = true
            botonCrearCancion.isVisible = true

            tv12.isVisible = true
            tv13.isVisible = true
            tv14.isVisible = true
            tv15.isVisible = true


            fragmentMapa?.view?.visibility = View.INVISIBLE
            botonGuardarUbicacion.isVisible = false
            ingresarLatitud.isVisible = false
            ingresarLongitud.isVisible = false
            botonBuscarEnMapa.isVisible = false

        }


    }

    fun actualizarCancion(){


        val ingresarDireccionCancion = findViewById<EditText>(R.id.ti_editar_direccionCancion)
        val titulo = findViewById<EditText>(R.id.editar_titulo)
        val genero = findViewById<EditText>(R.id.editar_genero)
        val duracion= findViewById<EditText>(R.id.editar_duracion)




        var objetoCancion = CancionDTO(
            cancion!!.uid.toString(),
            cancion!!.uid_usuario.toString(),

            ubicacionMapa,
            titulo.text.toString(),
            genero.text.toString(),
            duracion.text.toString(),

        )

        val nuevaCancion = hashMapOf<String,Any>(

            "ubicacion" to objetoCancion.ubicacion!!,
            "tituloCancion" to objetoCancion.tituloCancion!!,
            "generoCancion" to objetoCancion.generoCancion!!,
            "duracionCancion" to objetoCancion.duracionCancion!!,

        )

        val db = Firebase.firestore
        val referencia = db.collection("cancion")
            .document(cancion?.uid!!)

        db.runTransaction {  transaction ->
            //val documentoActual = transaction.get(referencia)
            transaction.update(referencia, nuevaCancion)
        }
            .addOnSuccessListener {
                val usuario1 = intent.getParcelableExtra<AutorDTO>("autores")!!
                abrirActividadConParametros(VerCanciones::class.java,usuario1)
                Log.i("transaccion", "Transaccion Completa")
            }
            .addOnFailureListener{
                Log.i("transaccion", "ERROR")
            }




    }

    fun abrirActividadConParametros(
        clase: Class<*>,
        usuario: AutorDTO,
    ){
        val intentExplicito = Intent(
            this,
            clase
        )
        intentExplicito.putExtra("autores",usuario)
        startActivityForResult(intentExplicito,CODIGO_RESPUESTA_INTENT_EXPLICITO)
    }

    fun anadirMarcador(latLng: LatLng, title: String){
        mapa.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(title)
            //.draggable(true)
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

    private val crearusuarioTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {


            val ingresarDireccionCancion = findViewById<EditText>(R.id.ti_editar_direccionCancion).text.toString().trim()
            val titulo = findViewById<EditText>(R.id.editar_titulo).text.toString().trim()
            val genero = findViewById<EditText>(R.id.editar_genero).text.toString().trim()
            val cancion = findViewById<EditText>(R.id.editar_duracion).text.toString().trim()


            val botonCrearCancion = findViewById<Button>(R.id.btn_editarCancion)
            botonCrearCancion.isEnabled = (

                            ingresarDireccionCancion.isNotEmpty() &&
                            titulo.isNotEmpty() &&
                            genero.isNotEmpty() &&
                            cancion.isNotEmpty() )
        }

        override fun afterTextChanged(s: Editable) {}
    }
}