package com.folu.jejakkaki.ui.detail

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ImageView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.folu.jejakkaki.R
import com.folu.jejakkaki.adapter.SectionsPagerAdapter
import com.folu.jejakkaki.databinding.ActivityDetail2Binding
import com.folu.jejakkaki.model.TamanData
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class Detail2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityDetail2Binding
    private var id: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetail2Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        hideSystemUI()

        val backButton = binding.btnBack

        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        id = intent.getIntExtra("id", 0)
        showTaman(id)
        setUpTabLayoutWithViewPager()
    }

    private fun showTaman(id: Int) {
        val selectedTaman = TamanData.taman.find { it.id == id }
        if (selectedTaman != null) {
            binding.ivLogo.setImageResource(selectedTaman.logo)
            binding.ivBg.setImageResource(selectedTaman.bgPic)
            binding.tvNamaTaman.text = selectedTaman.namaTaman

            if (selectedTaman.logo2 != null) {
                binding.ivLogo2.setImageResource(selectedTaman.logo2)
                binding.ivLogo2.visibility = View.VISIBLE
            }
        }
    }

    private fun setUpTabLayoutWithViewPager() {
        val icons = ArrayList(TAB_ICONS)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, id)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        viewPager.isUserInputEnabled = false
        val tabs: TabLayout = binding.tabLayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            val tabView = LayoutInflater.from(this).inflate(R.layout.tab_title, tabs, false)
            tabView.findViewById<ImageView>(R.id.tab_icon).setImageResource(icons[position])
            tab.customView = tabView
        }.attach()


        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val tabView = tab?.customView
                val icon = tabView?.findViewById<ImageView>(R.id.tab_icon)
                icon?.scaleX = 1.0f
                icon?.scaleY = 1.0f
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val tabView = tab?.customView
                val icon = tabView?.findViewById<ImageView>(R.id.tab_icon)
                icon?.scaleX = 1.2f // Scale factor for X axis
                icon?.scaleY = 1.2f // Scale factor for Y axis
            }
        })
        tabs.selectTab(tabs.getTabAt(0))
        scaleTabIcon(tabs.getTabAt(0))
    }

    private fun scaleTabIcon(tab: TabLayout.Tab?) {
        val tabView = tab?.customView
        val icon = tabView?.findViewById<ImageView>(R.id.tab_icon)
        icon?.scaleX = 1.2f // Scale factor for X axis
        icon?.scaleY = 1.2f // Scale factor for Y axis
    }

    private fun hideSystemUI() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    override fun onBackPressed() {
        finish()
    }

    companion object {
        @StringRes
        internal val TAB_ICONS = arrayListOf(
            R.drawable.ic_outline_info_40,
            R.drawable.ic_tabler_deer_40,
            R.drawable.ic_la_hiking_40,
        )
    }

}