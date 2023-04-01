package com.holyquran.alquran.common


import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class MyPreference() {

    companion object {
        var preferences: SharedPreferences? = null

        var editor: SharedPreferences.Editor? = null
        private var instance: MyPreference? = null

        fun with(context: Context): MyPreference {
            if (instance == null) {
                instance = Builder(context, null, -1).build()
            }
            return instance!!
        }

    }

    constructor(context: Context, name: String?, mode: Int) : this() {
        preferences = context.getSharedPreferences(name, mode)
        editor = preferences!!.edit()
    }


    fun save(key: String?, value: String?) {
        editor!!.putString(
            key,
            value
        ).apply()
    }

    fun save(key: String?, value: Int) {
        editor!!.putInt(
            key,
            value
        ).apply()
    }

    fun save(key: String, value: Boolean) {
        editor!!.putBoolean(
            key,
            value
        ).apply()
    }

    fun getInt(key: String, defValue: Int): Int {
        return preferences!!.getInt(key, defValue)
    }

    fun getString(key: String, defValue: String): String? {
        return preferences!!.getString(key, defValue)
    }

    fun getBoolean(key: String, defValue: Boolean): Boolean {
        return preferences!!.getBoolean(key, defValue)
    }


    private class Builder(context: Context?, name: String?, mode: Int) {
        private val context: Context
        private val mode: Int
        private val name: String?

        fun build(): MyPreference {
            return if (mode == -1 || name == null) {
                MyPreference(context, "PREFS", MODE_PRIVATE)
            } else MyPreference(context, name, mode)
        }

        init {
            requireNotNull(context) { "Context must not be null." }
            this.context = context.applicationContext
            this.name = name
            this.mode = mode
        }
    }

}