package com.holyquran.alquran.ui.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import com.holyquran.alquran.models.datamodels.Aya
import com.holyquran.alquran.models.repositories.StartupRepository

class SurahViewModel : ViewModel() {
    private val repository = StartupRepository()
    private val ayatsList = ArrayList<Aya>()
    fun getSurahList(context: Context) = repository.getJsonDataFromAsset(context)

    fun setAyaList(aya: List<Aya>) {
        ayatsList.clear()
        ayatsList.addAll(aya)
    }

    fun getAyaList(): List<Aya> {
        return ayatsList
    }

}