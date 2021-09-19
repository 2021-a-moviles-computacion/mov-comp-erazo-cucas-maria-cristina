package com.example.firebaseuno

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.firebaseuno.dto.FirestoreProductoDTO
import com.example.firebaseuno.dto.FirestoreRestauranteDTO
import com.example.firebaseuno.dto.FirestoreRestauranteOrdenDTO
import com.example.firebaseuno.dto.Restaurante
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.math.RoundingMode
import java.text.DecimalFormat

class EOrdenes : AppCompatActivity() {
    var arregloRestaurantes = arrayListOf<Restaurante>()

    var adaptadorRestaurantes: ArrayAdapter<Restaurante>?= null
    var restauranteSeleccionado: Restaurante? = null

    var arregloProductos = arrayListOf<Producto>()

    var adaptadorProductos: ArrayAdapter<Producto>? = null
    var productoSeleccionado: Producto? = null
    val arregloOrdenes: ArrayList<FirestoreRestauranteOrdenDTO> = ArrayList<FirestoreRestauranteOrdenDTO>()

    var posRestauranteSelec: Int? = null
    var posProductoSelec: Int? = null
    //var arregloTiposComida = arrayListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eordenes)
        if (adaptadorRestaurantes == null) {
            adaptadorRestaurantes = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                arregloRestaurantes
            )
            adaptadorRestaurantes?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            cargarRestaurantes()
        }

        if (adaptadorProductos == null) {
            adaptadorProductos = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                arregloProductos
            )
            adaptadorProductos?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            cargarProductos()
        }
        val sp_restaurantes=findViewById<Spinner>(R.id.sp_restaurantes)
        val sp_producto=findViewById<Spinner>(R.id.sp_producto)
        sp_restaurantes.onItemSelectedListener =
            object : AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    posRestauranteSelec = position
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {}

            }
        sp_producto.onItemSelectedListener =
            object : AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    posProductoSelec = position
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {}

            }


        val botonAniadir = findViewById<Button>(R.id.btn_anadir_lista_producto)
        botonAniadir.setOnClickListener{
            if (crearOrden(posProductoSelec, posRestauranteSelec)) {
                val adaptador = ArrayAdapter(
                    this, // Contexto
                    android.R.layout.simple_list_item_1, // Layout (visual)
                    arregloOrdenes // Arreglo
                )
                val listViewEjemplo = findViewById<ListView>(R.id.lv_lista_productos)
                listViewEjemplo.adapter = adaptador
                setearTotalOrdenes()
                vaciarFormulario()
            }
        }



        // cargarPrecio.text =








    }

    fun vaciarFormulario() {
        val sp_restaurantes=findViewById<Spinner>(R.id.sp_restaurantes)
        val sp_producto=findViewById<Spinner>(R.id.sp_producto)
        val et_cantidad_producto=findViewById<EditText>(R.id.et_cantidad_producto)
        sp_restaurantes.setSelection(0)
        sp_producto.setSelection(0)
        et_cantidad_producto.text.clear()
    }
    fun calcularTotalOrdenes(): Double?
    {
        var total = 0.0

        this.arregloOrdenes.forEach {
            val orden = it
            if (orden != null && orden.totalOrden != null)
                total = total + orden.totalOrden!!
        }

        return doubleDosDecimales(total)

    }
    fun doubleDosDecimales(numero: Double?): Double?
    {
        if(numero != null) {
            val df: DecimalFormat = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.CEILING
            return df.format(numero).toDouble()
        }
        else return null
    }

    fun setearTotalOrdenes()
    {
        val cargarPrecio = findViewById<TextView>(R.id.tv_totalPagar)
        if(arregloOrdenes.isEmpty()) {

            cargarPrecio.text = "$ 0.0"
        }else {
            cargarPrecio.text = "$ " + calcularTotalOrdenes().toString()
        }
    }

    fun totalPagar(){

    }



    fun crearOrden(posProductoSelec: Int?,posRestauranteSelec: Int?): Boolean
    {
        //crea un objetoo orden y lo añade a la variable global arregloOrdenes
        val cantidadProd= findViewById<EditText>(R.id.et_cantidad_producto)
        val cantidad = cantidadProd.text.toString().toIntOrNull()
        if (posProductoSelec != 0 && posRestauranteSelec != 0 && cantidad != null) {
            if (arregloProductos != null) {
                val precio = arregloProductos[posProductoSelec!!].precio
                //val nombreRes = arregloRestaurante[posRestauranteSelec!!].nombre
                val nombreProd = arregloProductos[posProductoSelec!!].nombre

                val ordenNueva = FirestoreRestauranteOrdenDTO(
                    nombreProd,
                    precio,
                    cantidad,
                    doubleDosDecimales(precio?.times(cantidad))



                )
                this.arregloOrdenes.add(ordenNueva)
                return true
            }
            else return false
        }
        else
            return false
    }



    fun cargarRestaurantes() {
        val spinnerRestaurantes = findViewById<Spinner>(R.id.sp_restaurantes)

        spinnerRestaurantes.adapter = adaptadorRestaurantes
        spinnerRestaurantes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                restauranteSeleccionado = arregloRestaurantes[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Log.i("firebase-firestore", "No seleccionó nada")
            }
        }

        val db = Firebase.firestore

        val referencia = db.collection("restaurante")
            .get()

        referencia
            .addOnSuccessListener {
                for (document in it) {
                    //Log.i("firebase-firestore", "${document.id} => ${document.data}")
                    var restaurante = document.toObject(Restaurante::class.java)

                    //restaurante.uid = document.id

                    arregloRestaurantes.add(Restaurante(restaurante.nombre))
                    adaptadorRestaurantes?.notifyDataSetChanged()
                }
            }
            .addOnFailureListener {

            }
    }


    fun cargarProductos() {
        val spinnerProductos = findViewById<Spinner>(R.id.sp_producto)

        spinnerProductos.adapter = adaptadorProductos
        spinnerProductos.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                productoSeleccionado = arregloProductos[position]

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Log.i("firebase-firestore", "No seleccionó nada")
            }
        }




        val db = Firebase.firestore

        val referencia = db.collection("producto")
            .get()
        referencia
            .addOnSuccessListener {
                for (document in it) {
                    //Log.i("firebase-firestore", "${document.id} => ${document.data}")
                    var producto = document.toObject(Producto::class.java)
                    //producto.uid = document.id

                    arregloProductos.add(Producto(producto.nombre, producto.precio))
                    adaptadorProductos?.notifyDataSetChanged()
                }
            }
            .addOnFailureListener {

            }
    }









}


