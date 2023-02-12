package com.holyquran.alquran.models.repositories

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.holyquran.alquran.models.datamodels.HolyQuran
import com.holyquran.alquran.models.datamodels.Quran
import java.io.IOException

class StartupRepository {

    private val TAG = "StartupRepository"

     fun getJsonDataFromAsset(context: Context): HolyQuran {
        lateinit var jsonString: String
        try {
            jsonString = context.assets.open("quran.json")
                .bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {
            Log.d(TAG, "getJsonDataFromAsset: ${ioException.localizedMessage}")
        }

        val listCountryType = object : TypeToken<HolyQuran>() {}.type
        return Gson().fromJson(jsonString, listCountryType)
    }
}