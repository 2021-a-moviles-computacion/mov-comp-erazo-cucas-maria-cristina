package com.example.deber02_skype

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import javax.xml.transform.ErrorListener

class FRecycleViewAdaptadorNombreMensaje(
    private val contexto: GRecyclerView,
    private val ListaContacto: List<BContactos>,
    private val recyclerView: RecyclerView,




): RecyclerView.Adapter<FRecycleViewAdaptadorNombreMensaje.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val nombre: TextView
//        val ultimaVez: TextView
//        val ultimaVezMensaje: TextView
//        val nombreReceptor: TextView
//        val mensaje1: TextView
//        val mensaje2: TextView
        val nombre:TextView

        val fecha: TextView
        val imagen: ImageView




        init {
//            nombre= view.findViewById(R.id.tv_nombre)
//            ultimaVez=view.findViewById(R.id.tv_ultimavez)
//            ultimaVezMensaje=view.findViewById(R.id.tv_ultimavezMensaje)
//            nombreReceptor=view.findViewById(R.id.tv_nombreReceptor)
//            mensaje1=view.findViewById(R.id.tv_mensaje1)
//            mensaje2= view.findViewById(R.id.tv_mensaje2)
            nombre= view.findViewById(R.id.tv_NombreIP)



            fecha=view.findViewById(R.id.tv_FechaMensaje)
            imagen=view.findViewById(R.id.imagenMensaje)


           }








        }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.recyclerview_interfazprincipal, //definir vista del recycler view
                parent,
                false
            )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val contacto = ListaContacto[position]


        holder.nombre.text =contacto.nombre
        holder.fecha.text= contacto.fecha
        holder.imagen.setImageResource(contacto.imagen)




    }



    override fun getItemCount(): Int {
        return  ListaContacto.size

    }





}



//Setear el layout que vamos a utilizar




    //tamanio del arreglo





