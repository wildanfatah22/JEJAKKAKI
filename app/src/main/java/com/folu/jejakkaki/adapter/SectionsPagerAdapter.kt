package com.folu.jejakkaki.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.folu.jejakkaki.ui.detail.fragments.Detail2Fragment
import com.folu.jejakkaki.ui.detail.fragments.FragmentType

class SectionsPagerAdapter(fragment: FragmentActivity, private val id: Int) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        val fragmentType = when (position) {
            0 -> FragmentType.INFO
            1 -> FragmentType.ANIMALS
            else -> FragmentType.ACTIVITIES
        }
        return Detail2Fragment.newInstance(id, fragmentType)
    }
}
