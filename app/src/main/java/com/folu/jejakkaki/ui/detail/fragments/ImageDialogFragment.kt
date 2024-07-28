package com.folu.jejakkaki.ui.detail.fragments

import android.app.Dialog
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.core.view.WindowCompat
import androidx.fragment.app.DialogFragment
import com.folu.jejakkaki.databinding.FragmentImageDialogBinding

class ImageDialogFragment : DialogFragment() {

    private var _binding: FragmentImageDialogBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val IMAGE_RES_ID = "image_res_id"

        fun newInstance(imageResId: Int): ImageDialogFragment {
            val fragment = ImageDialogFragment()
            val args = Bundle()
            args.putInt(IMAGE_RES_ID, imageResId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageDialogBinding.inflate(inflater, container, false)
        val imageResId = arguments?.getInt(IMAGE_RES_ID)
        imageResId?.let { binding.imageView.setImageResource(it) }

        binding.root.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val viewRect = Rect()
                binding.imageView.getGlobalVisibleRect(viewRect)
                if (!viewRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    dismiss()
                    return@setOnTouchListener true
                }
            }
            view.performClick()
            false
        }

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        dialog?.window?.decorView?.let { decorView ->
            WindowCompat.setDecorFitsSystemWindows(dialog!!.window!!, false)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                dialog!!.window!!.insetsController?.apply {
                    hide(WindowInsets.Type.statusBars())
                    systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                }
            } else {
                @Suppress("DEPRECATION")
                decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}