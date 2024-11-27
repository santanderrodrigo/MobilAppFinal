package com.example.fianalapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SocioAdapter(
    private val socios: List<Map<String, String>>,
    private val onLongClick: (Int) -> Unit
) : RecyclerView.Adapter<SocioAdapter.SocioViewHolder>() {

    class SocioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombre: TextView = itemView.findViewById(R.id.textViewNombre)
        val email: TextView = itemView.findViewById(R.id.textViewEmail)
        val telefono: TextView = itemView.findViewById(R.id.textViewTelefono)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SocioViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_socio, parent, false)
        return SocioViewHolder(view)
    }

    override fun onBindViewHolder(holder: SocioViewHolder, position: Int) {
        val socio = socios[position]
        holder.nombre.text = socio[DatabaseHelper.COLUMN_NAME]
        holder.email.text = socio[DatabaseHelper.COLUMN_EMAIL]
        holder.telefono.text = socio[DatabaseHelper.COLUMN_PHONE]
    }

    override fun getItemCount(): Int = socios.size
}