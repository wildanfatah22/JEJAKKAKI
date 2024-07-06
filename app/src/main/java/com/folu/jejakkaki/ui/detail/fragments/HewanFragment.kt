package com.folu.jejakkaki.ui.detail.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.folu.jejakkaki.R
import com.folu.jejakkaki.adapter.CarouselAdapter
import com.folu.jejakkaki.databinding.FragmentHewanBinding
import com.folu.jejakkaki.model.TamanData
import com.jackandphantom.carouselrecyclerview.CarouselLayoutManager

class HewanFragment : Fragment() {
    private var _binding: FragmentHewanBinding? = null
    private val binding get() = _binding!!
    private lateinit var carouselAdapter: CarouselAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHewanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt("id", 0)
        val selectedTaman = TamanData.taman.find { it.id == id }

        selectedTaman?.let { taman ->
            val animals = taman.animals.filterNotNull()
            val carouselItems = animals.map { Pair(it.pic, getColorResId(it.id)) }

            val carouselAdapter = CarouselAdapter(carouselItems) { imageResId ->
                val dialog = ImageDialogFragment.newInstance(imageResId)
                dialog.show(parentFragmentManager, "ImageDialogFragment")
            }
            binding.carousel.adapter = carouselAdapter
            binding.carousel.apply {
                setInfinite(true)
                setFlat(true)
                setIntervalRatio(0.5f)
            }

            val currentPosition = binding.carousel.getSelectedPosition()
            updateCaption(currentPosition)

            // Add an OnScrollListener to track changes in the carousel position
            binding.carousel.setItemSelectListener(object : CarouselLayoutManager.OnSelected {
                override fun onItemSelected(position: Int) {
                    // Update the caption when the carousel position changes
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
            else -> R.color.red // Default color
        }
    }

    private fun updateCaption(position: Int) {
        val taman = TamanData.taman.find { it.id == arguments?.getInt("id", 0) }
        val animal = taman?.animals?.getOrNull(position)
        val captionText = animal?.desc?.let { getString(it) } ?: ""
        binding.caption.text = captionText
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(id: Int): HewanFragment {
            val fragment = HewanFragment()
            val args = Bundle()
            args.putInt("id", id)
            fragment.arguments = args
            return fragment
        }
    }
}