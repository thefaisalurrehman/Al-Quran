package com.holyquran.alquran.ui.activity

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.holyquran.alquran.R
import com.holyquran.alquran.databinding.ActivityMainBinding
import com.holyquran.alquran.models.ISurah
import com.holyquran.alquran.models.adapter.SurahAdapter
import com.holyquran.alquran.models.datamodels.surah.SurahInfoItem
import com.holyquran.alquran.ui.fragments.AyatsFragment
import com.holyquran.alquran.ui.vm.SurahViewModel
import java.util.*

class MainActivity : AppCompatActivity(), ISurah, NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    val viewModel: SurahViewModel by viewModels()
    private lateinit var adapter: SurahAdapter
    private var fetchedList = ArrayList<SurahInfoItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        viewModel.getSurahList(applicationContext)
        viewModel.surahList.observe(this) {
            fetchedList.clear()
            fetchedList.addAll(it)
            adapter = SurahAdapter(it, this@MainActivity)
            binding.run {
                recyclerview.adapter = adapter
                recyclerview.layoutManager = LinearLayoutManager(this@MainActivity)
            }
        }

        viewModel.fetchCompleteQuran(applicationContext)

        binding.navView.setNavigationItemSelectedListener(this)

        binding.menuBtn.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        binding.searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                filter(p0.toString())
            }
        })

    }

    private fun filter(text: String) {
        //new array list that will hold the filtered data
        val filteredNames: ArrayList<SurahInfoItem> = ArrayList()

        for (s in fetchedList) {
            //if the existing elements contains the search input
            if (s.index.lowercase(Locale.getDefault())
                    .contains(text.lowercase(Locale.getDefault())) || s.title
                    .lowercase(Locale.getDefault())
                    .contains(text.lowercase(Locale.getDefault())) || s.titleAr
                    .lowercase(Locale.getDefault()).contains(text.lowercase(Locale.getDefault()))
            ) {
                filteredNames.add(s)
            }
        }

        adapter.filterList(filteredNames)
    }

    override fun onSurahClicked(position: Int) {
        viewModel.setAyaList(position)
        binding.frame.isVisible = true
        supportFragmentManager.beginTransaction().add(binding.frame.id, AyatsFragment())
            .addToBackStack("Aya").commit()
    }

    private fun backPress() {
        val count = supportFragmentManager.backStackEntryCount
        if (count <= 0) {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }
            super.onBackPressed()
        } else {
            binding.frame.isVisible = false
            supportFragmentManager.popBackStackImmediate()
        }
    }

    override fun onBackPressed() {
        backPress()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.rateUs -> {
                try {
                    val url =
                        "https://play.google.com/store/apps/details?id=${packageName}"
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            R.id.shareApp -> {
                shareApp()
            }
            R.id.feedback -> {
                sendEmail()
            }

        }
        return false
    }

    private fun sendEmail() {
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse("mailto:" + "decentinfoofficial@gmail.com")
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Al Quran Feedback")
        try {
            startActivity(Intent.createChooser(emailIntent, "Send email using..."))
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(this, "No email clients installed.", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun shareApp() {
        try {
            val shareBody: String
            val uri =
                "https://play.google.com/store/apps/details?id=$packageName"
            shareBody = getString(R.string.share_app_msg) + "\n\n" + uri
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            sharingIntent.putExtra(
                Intent.EXTRA_SUBJECT,
                resources.getString(R.string.app_name)
            )
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(sharingIntent, "Share Using"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}