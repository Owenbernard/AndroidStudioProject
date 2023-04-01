package com.example.playlistsound.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistsound.Model.ListModel
import com.example.playlistsound.R

class ListHolder(private val newList : ArrayList<ListModel>) :
    RecyclerView.Adapter<ListHolder.ListClassModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListClassModel {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_model, parent, false)
        return ListClassModel(itemView)
    }

    override fun getItemCount(): Int {
        return newList.size
    }

    override fun onBindViewHolder(holder: ListClassModel, position: Int) {
        val currentItem = newList[position]
        holder.image.setImageResource(currentItem.image)
        holder.nom.text = currentItem.nom
        holder.duree.text = currentItem.duree
    }


    class ListClassModel(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image: ImageView = itemView.findViewById<ImageView>(R.id.imageMsc)
        val nom: TextView = itemView.findViewById<TextView>(R.id.textView)
        val duree: TextView = itemView.findViewById<TextView>(R.id.textView2)
    }

}