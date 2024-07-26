package com.folu.jejakkaki.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.lifecycle.lifecycleScope
import com.folu.jejakkaki.R
import com.folu.jejakkaki.databinding.ActivityMapsBinding
import com.folu.jejakkaki.ui.maps.MapBaliActivity
import com.folu.jejakkaki.ui.maps.MapJawaActivity
import com.folu.jejakkaki.ui.maps.MapKalimantanActivity
import com.folu.jejakkaki.ui.maps.MapMalukuActivity
import com.folu.jejakkaki.ui.maps.MapPapuaActivity
import com.folu.jejakkaki.ui.maps.MapSulawesiActivity
import com.folu.jejakkaki.ui.maps.MapSumatraActivity
import com.folu.jejakkaki.ui.video.VideoActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import render.animations.Attention
import render.animations.Fade
import render.animations.Render

class MapsActivity : AppCompatActivity() {

    private lateinit var fab: ImageButton
    private lateinit var binding: ActivityMapsBinding
    private val bounceDurations = listOf(1000L, 1500L, 1200L, 2000L, 1800L, 1700L, 1900L)
//    private val idleTimeout: Long = 3 * 60 * 1000 // 3 minutes
//    private val handler = Handler(Looper.getMainLooper())
//    private val runnable = Runnable {
//        startVideoActivity()
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //startHandler()
        hideSystemUI()
        fab = binding.fabLanguage

        playAnimation()

        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val selectedLanguage = sharedPreferences.getString("selectedLanguage", "en") ?: "en"

        // Set the FAB icon based on the selected language
        setFabIconBasedOnLanguage(selectedLanguage)

        setPulauButtonListeners()

        fab.setOnClickListener {
            var dialog = LanguageFragment()
            dialog.show(supportFragmentManager, "changeLanguage")
        }
    }

//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        resetHandler() // Reset the idle timer on user interaction
//        return super.onTouchEvent(event)
//    }
//
//    private fun startHandler() {
//        handler.postDelayed(runnable, idleTimeout)
//    }
//
//    private fun resetHandler() {
//        handler.removeCallbacks(runnable)
//        handler.postDelayed(runnable, idleTimeout)
//    }
//
//    private fun startVideoActivity() {
//        val intent = Intent(this, VideoActivity::class.java)
//        startActivity(intent)
//    }

//    override fun onResume() {
//        super.onResume()
//        resetHandler()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        handler.removeCallbacks(runnable)
//    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun playAnimation() {
        val markerViews = listOf(
            binding.markerSumatra,
            binding.markerJawa,
            binding.markerBali,
            binding.markerKalimantan,
            binding.markerMaluku,
            binding.markerSulawesi,
            binding.markerPapua
        )

        val render = Render(this)

        lifecycleScope.launch(Dispatchers.Main) {
            while (isActive) {
                markerViews.forEachIndexed { index, view ->
                    // Bounce animation
                    render.setAnimation(Attention().Bounce(view))
                    render.setDuration(bounceDurations[index])
                    render.start()
                    delay(200L)
                }
            }
        }

        lifecycleScope.launch(Dispatchers.Main) {
            while (isActive) {
                render.setAnimation(Fade().Out(binding.tvLogo))
                render.start()
                delay(3000L)
                render.setAnimation(Fade().In(binding.tvLogo).setDuration(3000L))
                render.start()
                delay(5000L)
            }
        }
    }

    private fun setPulauButtonListeners() {
        binding.pulauSumatra.setOnClickListener { startPulauActivity(MapSumatraActivity::class.java) }
        binding.pulauKalimantan.setOnClickListener { startPulauActivity(MapKalimantanActivity::class.java) }
        binding.pulauJawa.setOnClickListener { startPulauActivity(MapJawaActivity::class.java) }
        binding.pulauBali.setOnClickListener { startPulauActivity(MapBaliActivity::class.java) }
        binding.pulauSulawesi.setOnClickListener { startPulauActivity(MapSulawesiActivity::class.java) }
        binding.pulauMaluku.setOnClickListener { startPulauActivity(MapMalukuActivity::class.java) }
        binding.pulauPapua.setOnClickListener { startPulauActivity(MapPapuaActivity::class.java) }
    }

    private fun startPulauActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        val optionsCompat: ActivityOptionsCompat =
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                Pair(binding.pulauSumatra, "pulauSumatra"),
                Pair(binding.pulauSiberut, "pulauSiberut"),
                Pair(binding.pulauBali, "pulauBali"),
                Pair(binding.pulauKalimantan, "pulauKalimantan"),
                Pair(binding.pulauPapua, "pulauPapua"),
                Pair(binding.pulauSulawesi, "pulauSulawesi"),
                Pair(binding.pulauJawa, "pulauJawa"),
                Pair(binding.pulauMaluku, "pulauMaluku"),
            )
        ActivityCompat.startActivity(this, intent, optionsCompat.toBundle())
    }

    private fun setFabIconBasedOnLanguage(language: String) {
        when (language) {
            "in" -> fab.setImageResource(R.drawable.flag_id)
            "en" -> fab.setImageResource(R.drawable.flag_english)
            else -> fab.setImageResource(R.drawable.flag_english) // Default
        }
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

    companion object {
        private const val TAG = "MapsActivity"
    }
}
