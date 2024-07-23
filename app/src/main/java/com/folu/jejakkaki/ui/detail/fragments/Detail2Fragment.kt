package com.folu.jejakkaki.ui.detail.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.folu.jejakkaki.R
import com.folu.jejakkaki.adapter.CarouselAdapter
import com.folu.jejakkaki.databinding.DialogImageBinding
import com.folu.jejakkaki.databinding.FragmentDetail2Binding
import com.folu.jejakkaki.model.TamanData
import com.jackandphantom.carouselrecyclerview.CarouselLayoutManager
import java.io.Serializable

class Detail2Fragment : Fragment() {

    private var _binding: FragmentDetail2Binding? = null
    private val binding get() = _binding!!
    private lateinit var carouselAdapter: CarouselAdapter
    private var fragmentType: FragmentType = FragmentType.INFO

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetail2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt("id", 0)
        fragmentType = arguments?.getSerializable("fragment_type") as FragmentType
        val selectedTaman = TamanData.taman.find { it.id == id }

        selectedTaman?.let { taman ->
            val carouselItems = when (fragmentType) {
                FragmentType.ACTIVITIES -> taman.activities.filterNotNull().map { Pair(it.pic, getColorResId(it.id)) }
                FragmentType.ANIMALS -> taman.animals.filterNotNull().map { Pair(it.pic, getColorResId(it.id)) }
                FragmentType.INFO -> listOfNotNull(
                    taman.car1?.let { Pair(it, R.color.red) },
                    taman.car2?.let { Pair(it, R.color.pink) },
                    taman.car3?.let { Pair(it, R.color.green) }
                )
            }

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

            if (fragmentType == FragmentType.INFO) {
                taman.info?.let { infoResId ->
                    binding.content.text = getString(infoResId)
                }
            }
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
        val captionText = when (fragmentType) {
            FragmentType.ACTIVITIES -> taman?.activities?.getOrNull(position)?.desc?.let { getString(it) } ?: ""
            FragmentType.ANIMALS -> taman?.animals?.getOrNull(position)?.desc?.let { getString(it) } ?: ""
            FragmentType.INFO -> "" // No caption needed for info
        }
        binding.caption.text = captionText
    }

    private fun showImageDialog(imageResId: Int) {
        val dialog = Dialog(requireContext())
        val dialogBinding = DialogImageBinding.inflate(layoutInflater)
        dialogBinding.imageViewDialog.setImageResource(imageResId)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val layoutParams = dialog.window?.attributes
        layoutParams?.dimAmount = 0.5f // Adjust the dim amount as needed
        dialog.window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(id: Int, fragmentType: FragmentType): Detail2Fragment {
            val fragment = Detail2Fragment()
            val args = Bundle()
            args.putInt("id", id)
            args.putSerializable("fragment_type", fragmentType)
            fragment.arguments = args
            return fragment
        }
    }
}

enum class FragmentType : Serializable {
    ACTIVITIES, ANIMALS, INFO
}