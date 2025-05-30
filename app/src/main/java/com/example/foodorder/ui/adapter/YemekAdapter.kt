package com.example.foodorder.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodorder.R
import com.example.foodorder.data.model.Yemek
import com.example.foodorder.utils.Constants

class YemekAdapter(
    private var yemekListesi: List<Yemek>,
    private val onItemClick: (Yemek) -> Unit
) : RecyclerView.Adapter<YemekAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageYemek: ImageView = view.findViewById(R.id.imageYemek)
        val textYemekAdi: TextView = view.findViewById(R.id.textYemekAdi)
        val textYemekFiyat: TextView = view.findViewById(R.id.textYemekFiyat)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_yemek, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val yemek = yemekListesi[position]

        holder.textYemekAdi.text = yemek.yemek_adi
        holder.textYemekFiyat.text = "${yemek.yemek_fiyat} ₺"

        Glide.with(holder.itemView.context)
            .load("${Constants.IMAGE_BASE_URL}${yemek.yemek_resim_adi}")
            .into(holder.imageYemek)

        holder.itemView.setOnClickListener {
            onItemClick(yemek)
        }
    }

    override fun getItemCount() = yemekListesi.size

    fun updateList(newList: List<Yemek>) {
        yemekListesi = newList
        notifyDataSetChanged()
    }
}