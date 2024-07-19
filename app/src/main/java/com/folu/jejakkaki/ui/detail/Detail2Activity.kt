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
import com.folu.jejakkaki.ui.detail.fragments.ImageDialogFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class Detail2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityDetail2Binding
    private var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetail2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemUI()

        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        id = intent.getIntExtra("id", 0)
        showTaman(id)
        setUpTabLayoutWithViewPager()
    }

    private fun showTaman(id: Int) {
        TamanData.taman.find { it.id == id }?.let { selectedTaman ->
            binding.apply {
                ivLogo.setImageResource(selectedTaman.logo)
                ivBg.setImageResource(selectedTaman.bgPic)
                tvNamaTaman.text = selectedTaman.namaTaman

                selectedTaman.logo2?.let {
                    ivLogo2.setImageResource(it)
                    ivLogo2.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setUpTabLayoutWithViewPager() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, id)
        binding.viewPager.apply {
            adapter = sectionsPagerAdapter
            isUserInputEnabled = false
        }

        val tabs: TabLayout = binding.tabLayout
        TabLayoutMediator(tabs, binding.viewPager) { tab, position ->
            val tabView = LayoutInflater.from(this).inflate(R.layout.tab_title, tabs, false)
            val imageView = tabView.findViewById<ImageView>(R.id.tab_icon)
            imageView.setImageResource(TAB_ICONS[position])
            tab.customView = tabView
        }.attach()

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.customView?.findViewById<ImageView>(R.id.tab_icon)?.apply {
                    scaleX = 1.0f
                    scaleY = 1.0f
                }
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.customView?.findViewById<ImageView>(R.id.tab_icon)?.apply {
                    scaleX = 1.6f
                    scaleY = 1.6f
                }
            }
        })

        // Set the first tab icon to be larger initially
        tabs.getTabAt(0)?.let { scaleTabIcon(it) }
    }

    private fun scaleTabIcon(tab: TabLayout.Tab) {
        tab.customView?.findViewById<ImageView>(R.id.tab_icon)?.apply {
            scaleX = 1.6f
            scaleY = 1.6f
        }
    }

    private fun hideSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            @Suppress("DEPRECATION")
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