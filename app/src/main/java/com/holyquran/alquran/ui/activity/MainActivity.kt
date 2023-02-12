package com.holyquran.alquran.ui.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.holyquran.alquran.databinding.ActivityMainBinding
import com.holyquran.alquran.models.ISurah
import com.holyquran.alquran.models.adapter.SurahAdapter
import com.holyquran.alquran.models.datamodels.Aya
import com.holyquran.alquran.ui.fragments.AyatsFragment
import com.holyquran.alquran.ui.vm.SurahViewModel

class MainActivity : AppCompatActivity(), ISurah {
    private lateinit var binding: ActivityMainBinding
    val viewModel: SurahViewModel by viewModels()
    private lateinit var adapter: SurahAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getSurahList(applicationContext).let {
            adapter = SurahAdapter(it.quran.sura, this)
        }

        binding.run {
            recyclerview.adapter = adapter
            recyclerview.layoutManager = LinearLayoutManager(this@MainActivity)
        }

    }

    override fun onSurahClicked(ayats: List<Aya>) {
        viewModel.setAyaList(ayats)
        binding.frame.isVisible = true
        supportFragmentManager.beginTransaction().add(binding.frame.id, AyatsFragment())
            .addToBackStack("Aya").commit()
    }

    private fun backPress() {
        val count = supportFragmentManager.backStackEntryCount
        if (count <= 0) {
            super.onBackPressed()
        } else {
            binding.frame.isVisible = false
            supportFragmentManager.popBackStackImmediate()
        }
    }

    override fun onBackPressed() {
        backPress()
    }
}