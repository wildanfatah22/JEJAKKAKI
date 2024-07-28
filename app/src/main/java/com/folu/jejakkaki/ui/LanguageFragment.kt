package com.folu.jejakkaki.ui

import android.app.Dialog
import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Button
import android.widget.Toast
import androidx.core.view.WindowCompat
import androidx.fragment.app.DialogFragment
import com.folu.jejakkaki.R
import com.folu.jejakkaki.databinding.FragmentLanguageBinding
import com.yariksoffice.lingver.Lingver

class LanguageFragment : DialogFragment() {

    private var _binding: FragmentLanguageBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLanguageBinding.inflate(inflater, container, false)

        binding.btnID.setOnClickListener {
            changeLanguage("in")
        }

        binding.btnENG.setOnClickListener {
            changeLanguage("en")
        }

        binding.root.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val viewRect = Rect()
                binding.shapeImage.getGlobalVisibleRect(viewRect)
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
                    systemBarsBehavior =
                        WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                }
            } else {
                @Suppress("DEPRECATION")
                decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            }
        }
    }

    private fun changeLanguage(language: String) {
        try {
            Lingver.getInstance().setLocale(requireActivity(), language)
            saveSelectedLanguage(language)
            dismiss()
            requireActivity().recreate()
            showToast(getString(R.string.ganti_bahasa))
        } catch (e: Exception) {
            showToast(getString(R.string.error_ganti_bahasa))
        }
    }

    private fun saveSelectedLanguage(language: String) {
        val sharedPreferences =
            requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("selectedLanguage", language)
            putBoolean("changedLanguage", true)
            apply()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return dialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}