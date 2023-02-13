package com.holyquran.alquran.models.repositories

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.holyquran.alquran.models.datamodels.HolyQuran
import com.holyquran.alquran.models.datamodels.surah.SurahInfoItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class StartupRepository {

    private val TAG = "StartupRepository"

    suspend fun getCompleteQuran(context: Context): HolyQuran {
        val jsonString: Deferred<HolyQuran> = CoroutineScope(Dispatchers.IO).async {
            val quran = context.assets.open("quran.json").bufferedReader().use { it.readText() }
            val listCountryType = object : TypeToken<HolyQuran>() {}.type
            Gson().fromJson(quran, listCountryType)
        }
        return jsonString.await()
    }


    suspend fun getSurahList(context: Context): List<SurahInfoItem> {
        val jsonString: Deferred<List<SurahInfoItem>> = CoroutineScope(Dispatchers.IO).async {
            val quran = context.assets.open("surah.json").bufferedReader().use { it.readText() }
            val listCountryType = object : TypeToken<List<SurahInfoItem>>() {}.type
            Gson().fromJson(quran, listCountryType)
        }
        return jsonString.await()
    }

}