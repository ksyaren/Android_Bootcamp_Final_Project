package com.example.foodorder.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodorder.R
import com.example.foodorder.data.model.SepetYemek
import com.example.foodorder.utils.Constants

class SepetAdapter(
    private var sepetYemekleri: List<SepetYemek>,
    private val onDeleteClick: (SepetYemek) -> Unit
) : RecyclerView.Adapter<SepetAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageYemek: ImageView = view.findViewById(R.id.imageYemek)
        val textYemekAdi: TextView = view.findViewById(R.id.textYemekAdi)
        val textYemekFiyat: TextView = view.findViewById(R.id.textYemekFiyat)
        val textAdet: TextView = view.findViewById(R.id.textAdet)
        val buttonSil: ImageView = view.findViewById(R.id.buttonSil)
        val textToplamFiyat: TextView = view.findViewById(R.id.textToplamFiyat)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sepet, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sepetYemek = sepetYemekleri[position]

        holder.textYemekAdi.text = sepetYemek.yemek_adi
        holder.textYemekFiyat.text = "${sepetYemek.yemek_fiyat} ₺"
        holder.textAdet.text = "Adet: ${sepetYemek.yemek_siparis_adet}"

        val toplamFiyat = sepetYemek.yemek_fiyat.toInt() * sepetYemek.yemek_siparis_adet.toInt()
        holder.textToplamFiyat.text = "Toplam: $toplamFiyat ₺"

        Glide.with(holder.itemView.context)
            .load("${Constants.IMAGE_BASE_URL}${sepetYemek.yemek_resim_adi}")
            .into(holder.imageYemek)

        holder.buttonSil.setOnClickListener {
            onDeleteClick(sepetYemek)
        }
    }

    override fun getItemCount() = sepetYemekleri.size

    fun updateList(newList: List<SepetYemek>) {
        sepetYemekleri = newList
        notifyDataSetChanged()
    }
}