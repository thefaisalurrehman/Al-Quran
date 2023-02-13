package com.holyquran.alquran.models.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.holyquran.alquran.databinding.SurahItemsBinding
import com.holyquran.alquran.models.ISurah
import com.holyquran.alquran.models.datamodels.surah.SurahInfoItem

class SurahAdapter(private val list: List<SurahInfoItem>, private val iSurah: ISurah) :
    RecyclerView.Adapter<SurahAdapter.SurahHolder>() {

    inner class SurahHolder(val binding: SurahItemsBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SurahHolder = SurahHolder(
        SurahItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: SurahHolder, position: Int) {

        holder.binding.run {
            surahNameARTV.text = list[position].titleAr
            surahNameENTV.text = list[position].title
            indexTV.text = list[position].index
            surahType.text = list[position].type
            totalVerse.text = "Verses :${list[position].count.toString().trimStart('0')}"
            place.text = "Place :${list[position].place}"
            root.setOnClickListener {
                iSurah.onSurahClicked(list[position].index.toInt() - 1)
            }
        }

    }
}