package com.holyquran.alquran.ui.vm

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.holyquran.alquran.common.Coroutines
import com.holyquran.alquran.models.datamodels.Aya
import com.holyquran.alquran.models.datamodels.HolyQuran
import com.holyquran.alquran.models.datamodels.surah.SurahInfoItem
import com.holyquran.alquran.models.repositories.StartupRepository

class SurahViewModel : ViewModel() {
    private val repository = StartupRepository()
    private val ayatsList = ArrayList<Aya>()
    private val quran = MutableLiveData<HolyQuran>()
    val surahList = MutableLiveData<List<SurahInfoItem>>()
    fun getSurahList(context: Context) =
        Coroutines.ioThenMain({ repository.getSurahList(context) }) {
            surahList.postValue(it)
        }

    fun getCompleteSurah(context: Context) =
        Coroutines.ioThenMain({ repository.getCompleteQuran(context) }) {
            quran.postValue(it)
        }

    fun fetchCompleteQuran(context: Context) =
        Coroutines.ioThenWork({ repository.getCompleteQuran(context.applicationContext) }) {
            quran.postValue(it)
        }

    fun setAyaList(position: Int) {
        val list = quran.value?.quran?.sura?.get(position)?.aya
        list?.let {
            ayatsList.clear()
            ayatsList.addAll(list)
        }

    }

    fun getAyaList(): List<Aya> {
        return ayatsList
    }

}