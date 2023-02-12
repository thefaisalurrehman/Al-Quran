package com.holyquran.alquran.models

import com.holyquran.alquran.models.datamodels.Aya

interface ISurah {
    fun onSurahClicked(ayats: List<Aya>)
}