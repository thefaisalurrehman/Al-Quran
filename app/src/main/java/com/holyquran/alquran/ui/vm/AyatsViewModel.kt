package com.holyquran.alquran.ui.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import com.holyquran.alquran.models.repositories.StartupRepository

class AyatsViewModel() : ViewModel() {
    private val repository = StartupRepository()
    fun getSurahList(context: Context) = repository.getJsonDataFromAsset(context)
}