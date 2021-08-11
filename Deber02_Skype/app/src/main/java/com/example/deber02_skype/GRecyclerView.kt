package com.example.deber02_skype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class GRecyclerView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grecycler_view)
        val imageEditar=findViewById<ImageView>(R.id.imageEditar)
        imageEditar.setOnClickListener{
            abrirActividad(EditarContacto::class.java)


        }
        val botoSecreto=findViewById<Button>(R.id.secretBtn)
        botoSecreto.setOnClickListener{
            abrirActividad(ChatPrincipal::class.java)
        }
        val botonNotificaciones= findViewById<ImageView>(R.id.btnNotificacion)
        botonNotificaciones.setOnClickListener {
            abrirActividad(IRaNotificaciones::class.java)
        }
        val chatLlamadas=findViewById<ImageView>(R.id.imagenLlamadas)
        chatLlamadas.setOnClickListener {
            abrirActividad(Llamadas::class.java)
        }
        val imaContactos=findViewById<ImageView>(R.id.ImagenContactos)
        imaContactos.setOnClickListener {
            abrirActividad(Contactos::class.java)
        }

        val listaEntrenador= arrayListOf<BContactos>()
        val ligaPokemin = DLiga("Kanto","12/10/2021",R.drawable.contacto2)
        listaEntrenador
            .add(
                BContactos(
                    "Aurora Sampedro\nHola como has estado estos ultimos dias....",

                    "12/10/2021",
                    R.drawable.contactochat,

                    ligaPokemin
                )
            )
        listaEntrenador
            .add(
                BContactos(
                    "Cristina Erazo\nRealizaste la prueba que teniamos pendiente....",

                    "12/10/2021",
                    R.drawable.contactochat,
                    ligaPokemin
                )
            )
        listaEntrenador
            .add(
                BContactos(
                    "Pilar Rosero\nRealizaste la compra que te pedi de favor...",

                    "12/10/2021",
                    R.drawable.contactochat,
                    ligaPokemin
                )
            )
        listaEntrenador
            .add(
                BContactos(
                    "Francis Roca\nEspero que hoy si te acuerdes de mi buen dia....",

                    "12/10/2021",
                    R.drawable.contactochat,
                    ligaPokemin
                )
            )
        listaEntrenador
            .add(
                BContactos(
                    "Mariana Rosero\nEspero que hoy si te acuerdes de mi buen dia....",

                    "12/09/2021",
                    R.drawable.contactochat,
                    ligaPokemin
                )
            )
        listaEntrenador
            .add(
                BContactos(
                    "Karen Cucas\nEspero que hoy si te acuerdes de mi buen dia....",

                    "12/12/2021",
                    R.drawable.contactochat,
                    ligaPokemin
                )
            )
        listaEntrenador
            .add(
                BContactos(
                    "Elisa Alcivar\nEspero que hoy si te acuerdes de mi buen dia....",

                    "12/10/2021",
                    R.drawable.contactochat,
                    ligaPokemin
                )
            )

        listaEntrenador
            .add(
                BContactos(
                    "Patricio Reina\nEspero que hoy si te acuerdes de mi buen dia....",

                    "07/10/2021",
                    R.drawable.contactochat,
                    ligaPokemin
                )
            )


        val recyclerViewEntrenador = findViewById<RecyclerView>(
            R.id.rv_mensajes
        )
        iniciarRecyclerView(
            listaEntrenador,
            this,
            recyclerViewEntrenador
        )

    }
    fun iniciarRecyclerView(
        lista: List<BContactos>,
        actividad: GRecyclerView,
        recyclerView: RecyclerView
    ){
        val adaptador = FRecycleViewAdaptadorNombreMensaje(
            actividad,
            lista,
            recyclerView
        )
        recyclerView.adapter= adaptador
        recyclerView.itemAnimator= androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager=androidx.recyclerview.widget.LinearLayoutManager(actividad)
        adaptador.notifyDataSetChanged()
    }
    fun abrirActividad(
        clase: Class<*>
    ) {
        val intentExplicito = Intent(
            this,
            clase
        )
        // this.startActivity(intent)
        startActivity(intentExplicito)
    }


}