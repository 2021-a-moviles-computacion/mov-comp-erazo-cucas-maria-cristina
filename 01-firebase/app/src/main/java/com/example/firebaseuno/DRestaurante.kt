package com.example.firebaseuno

import android.app.DownloadManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DRestaurante : AppCompatActivity() {
    var ultimoDocumento: DocumentSnapshot? =null
    var query: Query?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drestaurante)
        val botonCrear = findViewById<Button>(R.id.btn_crear_restaurante)
        botonCrear
            .setOnClickListener {
                crearRestaurante()
            }

        val datosPrueba = findViewById<Button>(R.id.btn_datos_prueba)
        datosPrueba
            .setOnClickListener {
                transaccion()
            }

        val botonConsultar = findViewById<Button>(R.id.btn_consulta)
        botonConsultar
            .setOnClickListener {
                consultar()
            }
    }

    fun crearRestaurante() {
        val editTextNombre = findViewById<EditText>(R.id.et_nombre_restaurante)
        val nuevoRestaurante = hashMapOf<String, Any>(
            "nombre" to editTextNombre.text.toString()
        )
        val db = Firebase.firestore
        val referencia = db.collection("restaurante")
        referencia
            .add(nuevoRestaurante)
            .addOnSuccessListener {
                editTextNombre.text.clear()
            }
            .addOnFailureListener { }
    }

    //vamos a crear una funcion para crear datos de prueba

    fun crearDatosPrueba() {
        val db = Firebase.firestore
        val cities = db.collection("cities")

        val data1 = hashMapOf(
            "name" to "San Francisco",
            "state" to "CA",
            "country" to "USA",
            "capital" to false,
            "population" to 860000,
            "regions" to listOf("west_coast", "norcal")
        )
        cities.document("SF").set(data1)

        val data2 = hashMapOf(
            "name" to "Los Angeles",
            "state" to "CA",
            "country" to "USA",
            "capital" to false,
            "population" to 3900000,
            "regions" to listOf("west_coast", "socal")
        )
        cities.document("LA").set(data2)

        val data3 = hashMapOf(
            "name" to "Washington D.C.",
            "state" to null,
            "country" to "USA",
            "capital" to true,
            "population" to 680000,
            "regions" to listOf("east_coast")
        )
        cities.document("DC").set(data3)

        val data4 = hashMapOf(
            "name" to "Tokyo",
            "state" to null,
            "country" to "Japan",
            "capital" to true,
            "population" to 9000000,
            "regions" to listOf("kanto", "honshu")
        )
        cities.document("TOK").set(data4)

        val data5 = hashMapOf(
            "name" to "Beijing",
            "state" to null,
            "country" to "China",
            "capital" to true,
            "population" to 21500000,
            "regions" to listOf("jingjinji", "hebei")
        )
        cities.document("BJ").set(data5)

    }

    fun consultar() {
        val db = Firebase.firestore
        var Refcities = db.collection("cities")
            .orderBy("population")
            .limit(2)
        var tarea: Task<QuerySnapshot>? =null
        if(query == null){
            tarea = Refcities.get()
        }else{
            tarea = query!!.get()
        }
        if (tarea != null){
            tarea
                .addOnSuccessListener {
                    documentSnapshot ->
                    guardarQuery(documentSnapshot, Refcities)
                    for(ciudad in  documentSnapshot){
                        Log.i("consultas", "${ciudad.data}")
                    }
                }
                .addOnFailureListener {
                    Log.i("consultas", "${it}")
                }
        }
        // podemos buscar mayores, menos y mpas cosas
      /*  citiesRef
            .document("BJ")
            .get()
            .addOnSuccessListener {
                Log.i("consultas", "${it.data}")
            }
        citiesRef
            .whereEqualTo("country", "China")
            .get()
            .addOnSuccessListener {
                for (ciudad in it) {
                    Log.i("consultas", "${ciudad.data}")
                    Log.i("consultas", "${ciudad.id}")
                }
            }

        // buscar por dos o mas elementos "array container"
        citiesRef
            .whereEqualTo("capital", "false")
            .whereArrayContainsAny("regions", arrayListOf("socal", "norcal")).get()
            .addOnSuccessListener {
                for (ciudad in it) {
                    Log.i("consultas", " == array-containers${ciudad.data}")

                }
            }*/

        //buscar por dos o mas rlementos mayor igual e igual
     /*   citiesRef
            .whereEqualTo("capital", "true")
            .whereGreaterThanOrEqualTo("population","1000000").get()
            .addOnSuccessListener {
                for(ciudad in it){
                    Log.i("consultas", " == array-containers${ciudad.data}")
                }
            }*/
/*
        citiesRef
            .whereEqualTo("capital", false)
            .whereGreaterThanOrEqualTo("population","4000000")
            .orderBy("population", Query.Direction.DESCENDING).get()
            .addOnSuccessListener {
                for(ciudad in it){
                    Log.i("consultas", " == array-containers${ciudad.data}")
                }
            }*/

    }

    fun guardarQuery(documentSnapshot: QuerySnapshot, Refcities: Query){
        if(documentSnapshot.size()>0){
            val ultimoDocumento = documentSnapshot.documents[documentSnapshot.size()-1]
            query = Refcities
                .startAfter(ultimoDocumento)
        }else{

        }
    }
    fun transaccion(){
        val db = Firebase.firestore
        val refCities =
        db.collection("cities")
            .document("BJ")
            db.runTransaction {
                transaction ->
                val documentoActual = transaction.get(refCities)
                val poblacion = documentoActual.getDouble("population")
                if(poblacion !=null){
                    val nuevaPoblacion = poblacion+1
                    transaction.update(refCities, "population", nuevaPoblacion)
                }
            }
                .addOnSuccessListener { Log.i("transaccion", "Transaccion completada") }
                .addOnFailureListener{Log.i("transaccion", "Error")}
    }


    }