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

class CrearCanciones : AppCompatActivity() {
    val CODIGO_RESPUESTA_INTENT_EXPLICITO = 401
    var usuarioIntent: AutorDTO?=null
    var ubicacionMapa: LatLng? = null
    private lateinit var mapa: GoogleMap
    var permisos = false
    var ArtistaSeleccionado: AutorDTO? =  null
    var latitud : Double? = null
    var longitud : Double? = null
    val arregloArtistas = arrayListOf<AutorDTO>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_canciones)
        val fragmentMapa = supportFragmentManager.findFragmentById(R.id.fr_CrearCancionMapa) as SupportMapFragment
        View.INVISIBLE.also { fragmentMapa?.view?.visibility = it }
        solicitarPermisos()

        usuarioIntent = intent.getParcelableExtra<AutorDTO>("autores")



        val direccion = findViewById<EditText>(R.id.ti_direccion)
        val titulo= findViewById<EditText>(R.id.ti_titulo)
        val genero = findViewById<EditText>(R.id.ti_genero)
        val duracion = findViewById<EditText>(R.id.ti_duracion)




        direccion.addTextChangedListener(crearusuarioTextWatcher)
        titulo.addTextChangedListener(crearusuarioTextWatcher)
        genero.addTextChangedListener(crearusuarioTextWatcher)
        duracion.addTextChangedListener(crearusuarioTextWatcher)


        val botonCrearCancion = findViewById<Button>(R.id.btn_CrearCancion)
        botonCrearCancion.isEnabled = false

        botonCrearCancion
            .setOnClickListener {
                crearCancion()
            }

        val botonCrearUbicacion = findViewById<Button>(R.id.btn_ir_crearDireccionMapa)
        botonCrearUbicacion
            .setOnClickListener {
                mapa()
            }





    }
    fun mapa(){

        val direccion = findViewById<EditText>(R.id.ti_direccion)
        val titulo = findViewById<EditText>(R.id.ti_titulo)
        val genero = findViewById<EditText>(R.id.ti_genero)
        val duracion = findViewById<EditText>(R.id.ti_duracion)


        val ingresarLatitud = findViewById<EditText>(R.id.ti_latitud_crear)
        val ingresarLongitud = findViewById<EditText>(R.id.ti_longitud_crear)

        visibilidad(true)
        val fragmentMapa = supportFragmentManager.findFragmentById(R.id.fr_CrearCancionMapa) as SupportMapFragment
        fragmentMapa.getMapAsync{ googleMap ->
            if(googleMap!=null){
                mapa = googleMap
                establecerConfiguracionMapa()
                val quicentro = LatLng(-0.176125, -78.480208)
                val titulo = "Quicentro"
                val zoom = 10f
                //anadirMarcador(quicentro,titulo)
                moverCamaraZoom(quicentro, zoom)
                escucharListeners()
            }
        }



        val botonBuscarEnMapa = findViewById<Button>(R.id.bnt_BuscarEnMapa)
        botonBuscarEnMapa
            .setOnClickListener {
                mapa.clear()
                //buscar en el mapa un lugar
                //que sera ingresada por el usuario
                val buscar = LatLng(ingresarLatitud.text.toString().toDouble(), ingresarLongitud.text.toString().toDouble())
                val zoom = 17f
                anadirMarcador(buscar,"Direccion")
                moverCamaraZoom(buscar,zoom)
            }


        val botonGuardarUbicacion = findViewById<Button>(R.id.btn_guardarUbicacion)
        botonGuardarUbicacion
            .setOnClickListener {
                visibilidad(false)
                direccion.setText("${ingresarLatitud.text.toString()} ; ${ingresarLongitud.text.toString()}")
                ubicacionMapa = LatLng(ingresarLatitud.text.toString().toDouble(), ingresarLongitud.text.toString().toDouble())

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

    // establecer el marcador
    fun anadirMarcador(latLng: LatLng, title: String){
        mapa.clear()
        mapa.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(title)
            //.draggable(true)
        )
    }
    // centrar la camara y el nivel de zoom
    fun moverCamaraZoom(latLng: LatLng, zoom: Float ){
        mapa.moveCamera(
            CameraUpdateFactory
                .newLatLngZoom(latLng, zoom)
        )

    }

    fun visibilidad(mapa:Boolean){


        val direccion = findViewById<EditText>(R.id.ti_direccion)
        val titulo = findViewById<EditText>(R.id.ti_titulo)
        val genero = findViewById<EditText>(R.id.ti_genero)
        val duracion = findViewById<EditText>(R.id.ti_duracion)

        val botonCrearUbicacion = findViewById<Button>(R.id.btn_ir_crearDireccionMapa)
        val botonCrearCancion = findViewById<Button>(R.id.btn_CrearCancion)
        val fragmentMapa = supportFragmentManager.findFragmentById(R.id.fr_CrearCancionMapa) as SupportMapFragment
        val botonGuardarUbicacion = findViewById<Button>(R.id.btn_guardarUbicacion)
        val ingresarLatitud = findViewById<EditText>(R.id.ti_latitud_crear)
        val ingresarLongitud = findViewById<EditText>(R.id.ti_longitud_crear)
        val botonBuscarEnMapa = findViewById<Button>(R.id.bnt_BuscarEnMapa)


        //fragmentMapa?.view?.visibility = View.GONE


        if(mapa){

            direccion.isVisible = false
            titulo.isVisible = false
            genero.isVisible = false
            duracion.isVisible = false
            botonCrearUbicacion.isVisible = false
            botonCrearCancion.isVisible = false


            fragmentMapa?.view?.visibility = View.VISIBLE
            botonGuardarUbicacion.isVisible = true
            ingresarLatitud.isVisible = true
            ingresarLongitud.isVisible = true
            botonBuscarEnMapa.isVisible = true
        }else{

            direccion.isVisible = true
            titulo.isVisible = true
            genero.isVisible = true
            duracion.isVisible = true

            botonCrearUbicacion.isVisible = true
            botonCrearCancion.isVisible = true


            fragmentMapa?.view?.visibility = View.INVISIBLE
            botonGuardarUbicacion.isVisible = false
            ingresarLatitud.isVisible = false
            ingresarLongitud.isVisible = false
            botonBuscarEnMapa.isVisible = false

        }


    }
// cuando demos clic a un marcador
    //cuando damos clic en el marcador vamos a saber
    // a q marcador le dimos clic

    fun escucharListeners(){

        mapa.setOnMarkerClickListener {
            //Log.i("mapa" ,"setOnMarkerClickListener ${it}")
            val ingresarLatitud = findViewById<EditText>(R.id.ti_latitud_crear)
            val ingresarLongitud = findViewById<EditText>(R.id.ti_longitud_crear)
            ingresarLatitud.setText(it.position.latitude.toString())
            ingresarLongitud.setText(it.position.longitude.toString())
            Log.i("mapa" ,"latitud:  ${it.position.latitude}")
            Log.i("mapa" ,"logitud:  ${it.position.longitude}")

            return@setOnMarkerClickListener true
        }

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



    fun crearCancion(){


        val direccion = findViewById<EditText>(R.id.ti_direccion)
        val titulo = findViewById<EditText>(R.id.ti_titulo)
        val genero = findViewById<EditText>(R.id.ti_genero)
        val duracion = findViewById<EditText>(R.id.ti_duracion)




        var objetoCancion = CancionDTO(
            null,
            usuarioIntent!!.uid.toString(),

            ubicacionMapa,
            titulo.text.toString().toString(),
            genero.text.toString().toString(),
            duracion.text.toString().toString(),

            )

        val nuevaCancion = hashMapOf<String,Any>(
            "usuario_uid" to objetoCancion.uid_usuario!!,

            "ubicacion" to objetoCancion.ubicacion!!,
            "tituloCancion" to objetoCancion.tituloCancion!!,
            "generoCancion" to objetoCancion.generoCancion!!,
            "duracionCancion" to objetoCancion.duracionCancion!!,

            )

        val db = Firebase.firestore
        val referencia = db.collection("canciones")
        referencia
            .add(nuevaCancion)
            .addOnSuccessListener {
                //editTextNombre.text.clear()
                abrirActividadConParametros(VerCanciones::class.java,usuarioIntent!!)
            }
            .addOnFailureListener{}


    }



    private val crearusuarioTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {


            val ingresarDireccionCancion = findViewById<EditText>(R.id.ti_direccion).text.toString().trim()
            val titulo = findViewById<EditText>(R.id.ti_titulo).text.toString().trim()
            val genero = findViewById<EditText>(R.id.ti_genero).text.toString().trim()
            val duracion = findViewById<EditText>(R.id.ti_duracion).text.toString().trim()



            val botonCrearCancion = findViewById<Button>(R.id.btn_CrearCancion)
            botonCrearCancion.isEnabled = (

                    ingresarDireccionCancion.isNotEmpty() &&
                            titulo.isNotEmpty() &&
                            genero.isNotEmpty() &&
                            duracion.isNotEmpty())
        }

        override fun afterTextChanged(s: Editable) {}
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



}

private fun Boolean.toInt(): Int {
    return if(this){
        1
    }else{
        0
    }
}








