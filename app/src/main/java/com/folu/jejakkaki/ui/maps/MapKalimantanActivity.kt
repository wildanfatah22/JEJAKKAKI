package com.folu.jejakkaki.ui.maps

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.folu.jejakkaki.R
import com.folu.jejakkaki.databinding.ActivityMapKalimantanBinding
import com.folu.jejakkaki.model.Place
import com.folu.jejakkaki.model.PlaceData
import com.folu.jejakkaki.ui.detail.Detail2Activity

class MapKalimantanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMapKalimantanBinding
    private var currentPreviewPlace: Place? = null
    private var infoWindowPopup: PopupWindow? = null
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapKalimantanBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        hideSystemUI()

        val placeButtons = arrayOf(
            binding.placeButton1,
            binding.placeButton2,
            binding.placeButton3,
            binding.placeButton4,
            binding.placeButton5,
            binding.placeButton6,
            binding.placeButton7,
            binding.placeButton8,
        )
        for (button in placeButtons) {
            button.setOnClickListener {
                val placeIdString = it.tag as? String
                val placeId = placeIdString?.toIntOrNull()
                val place = PlaceData.list.find { place -> place.id == placeId }
                showPreview(place, it)
            }
        }

        binding.backgroundOverlay.setOnClickListener {
            hidePreview()
            binding.backgroundOverlay.visibility = View.GONE
        }

        val btnBack = binding.btnBack

        btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun showPreview(place: Place?, view: View) {
        if (place == null) return

        // Hide previous preview if any
        hidePreview()

        // Show new preview
        val inflater = layoutInflater
        val previewView = inflater.inflate(R.layout.custom_info_window, null)

        // Customize previewView with place details
        val titleTextView = previewView.findViewById<TextView>(R.id.info_window_title)
        titleTextView.text = place.name

        val imageView = previewView.findViewById<ImageView>(R.id.info_window_image)
        imageView.setImageResource(place.image)

        // Measure the preview view dimensions
        previewView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        val popupWidth = previewView.measuredWidth
        val popupHeight = previewView.measuredHeight

        // Calculate position to center the preview above the clicked button
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        val buttonX = location[0]
        val buttonY = location[1]

        // Calculate the offset to center the popup above the button
        val xOff = buttonX + (view.width / 2) - (popupWidth / 2)
        val yOff = buttonY - popupHeight

        // Show preview as a PopupWindow
        infoWindowPopup = PopupWindow(previewView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        infoWindowPopup?.showAtLocation(view, 0, xOff, yOff)

        // Store current preview place
        currentPreviewPlace = place

        // Set OnClickListener for preview to open DetailActivity when clicked
        previewView.setOnClickListener {
            val intent = Intent(this, Detail2Activity::class.java)
            intent.putExtra("id", place.id)
            startActivity(intent)
        }

        // Close the popup when clicking outside
        val mainLayout = findViewById<View>(R.id.mainLayout)
        mainLayout.setOnClickListener {
            hidePreview()
            binding.backgroundOverlay.visibility = View.GONE
        }

        val btnBack = binding.btnBack

        btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun hidePreview() {
        infoWindowPopup?.dismiss()
        currentPreviewPlace = null
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

    override fun onDestroy() {
        super.onDestroy()
        // Remove callbacks to avoid memory leaks
        handler.removeCallbacksAndMessages(null)
    }

    override fun onBackPressed() {
        finish()
    }
}