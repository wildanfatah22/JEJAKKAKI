package com.folu.jejakkaki.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.folu.jejakkaki.ui.detail.fragments.AktifitasFragment
import com.folu.jejakkaki.ui.detail.fragments.HewanFragment
import com.folu.jejakkaki.ui.detail.fragments.InfoFragment

class SectionsPagerAdapter(fragment: FragmentActivity, private val id: Int) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        val fragment: Fragment = when (position) {
            0 -> InfoFragment()
            1 -> HewanFragment()
            else -> AktifitasFragment()
        }

        fragment.arguments = Bundle().apply {
            putInt("id", id)
        }

        return fragment
    }
}