package com.folu.jejakkaki.ui.detail.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.folu.jejakkaki.R
import com.folu.jejakkaki.adapter.CarouselAdapter
import com.folu.jejakkaki.databinding.DialogImageBinding
import com.folu.jejakkaki.databinding.FragmentAktifitasBinding
import com.folu.jejakkaki.model.TamanData
import com.jackandphantom.carouselrecyclerview.CarouselLayoutManager

class AktifitasFragment : Fragment() {

    private var _binding: FragmentAktifitasBinding? = null
    private val binding get() = _binding!!
    private lateinit var carouselAdapter: CarouselAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAktifitasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt("id", 0)
        val selectedTaman = TamanData.taman.find { it.id == id }

        selectedTaman?.let { taman ->
            val activities = taman.activities.filterNotNull()
            val carouselItems = activities.map { Pair(it.pic, getColorResId(it.id)) }

            carouselAdapter = CarouselAdapter(carouselItems) { imageResId ->
                showImageDialog(imageResId)
            }
            binding.carousel.adapter = carouselAdapter
            binding.carousel.apply {
                setInfinite(true)
                setFlat(true)
                setIntervalRatio(0.5f)
            }

            val currentPosition = binding.carousel.getSelectedPosition()
            updateCaption(currentPosition)

            binding.carousel.setItemSelectListener(object : CarouselLayoutManager.OnSelected {
                override fun onItemSelected(position: Int) {
                    updateCaption(position)
                }
            })
        }
    }

    private fun getColorResId(id: Int): Int {
        return when (id) {
            1 -> R.color.red
            2 -> R.color.pink
            3 -> R.color.green
            4 -> R.color.yellow
            5 -> R.color.offwhite
            else -> R.color.red
        }
    }

    private fun updateCaption(position: Int) {
        val taman = TamanData.taman.find { it.id == arguments?.getInt("id", 0) }
        val aktifitas = taman?.activities?.getOrNull(position)
        val captionText = aktifitas?.desc?.let { getString(it) } ?: ""
        binding.caption.text = captionText
    }

    private fun showImageDialog(imageResId: Int) {
        val dialog = Dialog(requireContext())
        val dialogBinding = DialogImageBinding.inflate(layoutInflater)
        dialogBinding.imageViewDialog.setImageResource(imageResId)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(id: Int): AktifitasFragment {
            val fragment = AktifitasFragment()
            val args = Bundle()
            args.putInt("id", id)
            fragment.arguments = args
            return fragment
        }
    }
}