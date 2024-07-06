package com.folu.jejakkaki.ui.detail.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.folu.jejakkaki.R
import com.folu.jejakkaki.adapter.CarouselAdapter
import com.folu.jejakkaki.databinding.FragmentInfoBinding
import com.folu.jejakkaki.model.TamanData

class InfoFragment : Fragment() {

    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!
    private var id: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        id = arguments?.getInt("id", 0)
        val selectedTaman = TamanData.taman.find { it.id == id }
        Log.d("selectedTaman", selectedTaman.toString())

        selectedTaman?.let { taman ->
            taman.info?.let { infoResId ->
                binding.content.text = getString(infoResId)
            }

            val carouselItems = listOfNotNull(
                taman.car1?.let { Pair(it, R.color.red) },
                taman.car2?.let { Pair(it, R.color.pink) },
                taman.car3?.let { Pair(it, R.color.green) }
            )

            val carouselAdapter = CarouselAdapter(carouselItems)
            binding.carousel.adapter = carouselAdapter
            binding.carousel.apply {
                setInfinite(true)
                setFlat(true)
                setIntervalRatio(0.5f)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(id: Int): InfoFragment {
            val fragment = InfoFragment()
            val args = Bundle()
            args.putInt("id", id)
            fragment.arguments = args
            return fragment
        }
    }
}