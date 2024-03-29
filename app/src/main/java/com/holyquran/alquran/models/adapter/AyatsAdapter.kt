package com.holyquran.alquran.models.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.holyquran.alquran.databinding.AyaItemsBinding
import com.holyquran.alquran.models.datamodels.Aya
import com.holyquran.alquran.models.interfaces.IAdapterPosition

class AyatsAdapter(private val list: List<Aya>, private val iIAdapterPosition: IAdapterPosition) :
    RecyclerView.Adapter<AyatsAdapter.AyatsHolder>() {

    inner class AyatsHolder(val binding: AyaItemsBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AyatsHolder = AyatsHolder(
        AyaItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: AyatsHolder, position: Int) {
        iIAdapterPosition.currentPosition(position)
        holder.binding.ayaTV.text = list[position].text
        holder.binding.indexTV.text = list[position].index
    }
}