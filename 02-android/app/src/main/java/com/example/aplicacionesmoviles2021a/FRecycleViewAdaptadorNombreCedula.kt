package com.example.aplicacionesmoviles2021a

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FRecycleViewAdaptadorNombreCedula(
    private val contexto: GRecyclerView,
    private val listaEntrenador: List<BEntrenador>,
    private val recyclerView: RecyclerView

):RecyclerView.Adapter<FRecycleViewAdaptadorNombreCedula.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreTextView: TextView
        val cedulaTextView: TextView
        val likesTextView: TextView
        val accionButton: TextView
        var numeroLikes = 0

        init {
            nombreTextView = view.findViewById(R.id.tv_nombre)
            cedulaTextView = view.findViewById(R.id.tv_cedula)
            accionButton = view.findViewById(R.id.btn_dar_like)
            likesTextView = view.findViewById(R.id.tv_like)
            accionButton.setOnClickListener{
                this.anadirLike()
            }



        }

        fun anadirLike(){
            this.numeroLikes = this.numeroLikes+1
            likesTextView.text= this.numeroLikes.toString()
            contexto.aumentarTotalLikes()
        }

    }




    //Setear el layout que vamos a utilizar
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.recycler_view_vista, //definir vista del recycler view
                parent,
                false
            )
        return MyViewHolder(itemView)
    }




    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       // es aqui donde ponemos una imagen, el nombre
        val entrenador = listaEntrenador[position]
        holder.nombreTextView.text =entrenador.nombre
        holder.cedulaTextView.text=entrenador.descripcion
        holder.accionButton.text="Like ${entrenador.nombre}"
        holder.likesTextView.text="0"
    }
//tamanio del arreglo
    override fun getItemCount(): Int {

        return  listaEntrenador.size
    }

}



