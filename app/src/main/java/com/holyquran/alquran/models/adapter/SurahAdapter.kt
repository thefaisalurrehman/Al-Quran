package com.holyquran.alquran.models.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.holyquran.alquran.databinding.SurahItemsBinding
import com.holyquran.alquran.models.ISurah
import com.holyquran.alquran.models.datamodels.Sura

class SurahAdapter(private val list: List<Sura>, private val iSurah: ISurah) :
    RecyclerView.Adapter<SurahAdapter.SurahHolder>() {

    inner class SurahHolder(val binding: SurahItemsBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SurahHolder = SurahHolder(
        SurahItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: SurahHolder, position: Int) {

        holder.binding.run {
            surahNameTV.text = list[position].name
            indexTV.text = list[position].index
            root.setOnClickListener {
                iSurah.onSurahClicked(list[position].aya)
            }
        }

    }
}