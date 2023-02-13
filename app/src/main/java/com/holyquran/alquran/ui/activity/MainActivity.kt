package com.holyquran.alquran.ui.activity

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
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
import com.holyquran.alquran.ui.fragments.AyatsFragment
import com.holyquran.alquran.ui.vm.SurahViewModel

class MainActivity : AppCompatActivity(), ISurah, NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    val viewModel: SurahViewModel by viewModels()
    private lateinit var adapter: SurahAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getSurahList(applicationContext)
        viewModel.surahList.observe(this) {
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