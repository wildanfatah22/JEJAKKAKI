package com.folu.jejakkaki.ui.detail.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.folu.jejakkaki.R

class ImageDialogFragment : DialogFragment() {

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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_image_dialog, container, false)
        val imageView = view.findViewById<ImageView>(R.id.imageView)
        val imageResId = arguments?.getInt(IMAGE_RES_ID)
        imageResId?.let { imageView.setImageResource(it) }
        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return dialog
    }
}