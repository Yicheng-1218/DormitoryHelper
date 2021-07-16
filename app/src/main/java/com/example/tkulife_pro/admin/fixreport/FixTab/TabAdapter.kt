package com.example.tkulife_pro.admin.fixreport.FixTab

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.util.ArrayList

class TabAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, private val fragments: ArrayList<Fragment>): FragmentStateAdapter(fragmentManager,lifecycle){

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    interface OnItemClick{
        fun onItemClick(position: Int)
    }
}