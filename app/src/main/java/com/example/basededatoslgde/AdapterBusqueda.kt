package com.example.basededatoslgde

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

open class AdapterBusqueda(context: Context?, var objetos: ArrayList<Objeto>?) : RecyclerView.Adapter<AdapterBusqueda.ViewHolder>() {

    val inflador = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = inflador.inflate(R.layout.elemento, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: AdapterBusqueda.ViewHolder, position: Int) {
        holder.precio.text = objetos!![position].precio.toString()
        holder.codigo.text = objetos!![position].codigo.toString()
        holder.descripcion.text = objetos!![position].descripcion
    }

    override fun getItemCount(): Int { return objetos!!.size }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val codigo = itemView.findViewById<TextView>(R.id.codigo)
        val descripcion = itemView.findViewById<TextView>(R.id.descripcion)
        val precio = itemView.findViewById<TextView>(R.id.precio)
    }
}
