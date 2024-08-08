package com.folu.jejakkaki.ui.video

import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.folu.jejakkaki.R
import com.folu.jejakkaki.databinding.ActivityVideoBinding

//class VideoActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityVideoBinding
//    private lateinit var player: ExoPlayer
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityVideoBinding.inflate(layoutInflater)
//        val view = binding.root
//        setContentView(view)
//
//        hideSystemUI()
//
//        // Initialize ExoPlayer
//        player = ExoPlayer.Builder(this).build()
//        binding.playerView.useController = false
//        binding.playerView.player = player
//
//        // Load the video
//        val videoUri = "android.resource://${packageName}/${R.raw.video}"
//        val mediaItem = MediaItem.fromUri(videoUri)
//        player.setMediaItem(mediaItem)
//        player.prepare()
//        player.play()
//
//        // Listener for video completion
//        player.addListener(object : Player.Listener {
//            override fun onPlaybackStateChanged(state: Int) {
//                if (state == Player.STATE_ENDED) {
//                    finish() // Return to MainActivity when video ends
//                }
//            }
//        })
//        hideSystemUI()
//    }
//
//    private fun hideSystemUI() {
//        WindowCompat.setDecorFitsSystemWindows(window, false)
//        WindowInsetsControllerCompat(window, binding.playerView).let { controller ->
//            controller.hide(WindowInsetsCompat.Type.systemBars())
//            controller.systemBarsBehavior =
//                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//        }
//    }
//
//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        finish() // Return to MainActivity on user interaction
//        return super.onTouchEvent(event)
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        player.release()
//    }
//}