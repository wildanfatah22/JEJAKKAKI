package com.folu.jejakkaki.ui.detail.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.folu.jejakkaki.R
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

        // Menyembunyikan status bar
        dialog?.window?.decorView?.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_FULLSCREEN

        binding.btnBack.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
