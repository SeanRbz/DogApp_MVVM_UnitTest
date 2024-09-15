package com.example.dogapp.views.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.dogapp.views.home.HomeViewPagerFragment


class HomeViewAdapter(frm: FragmentActivity) : FragmentStateAdapter(frm) {
    val fragments: ArrayList<Pair<String,Fragment>> = arrayListOf()

    fun addTabPage(newTab: String) {
        fragments.add(Pair(newTab,addFragment(newTab)))
    }

    fun getTabTitle(position: Int) :String = fragments.get(position).first

    override fun createFragment(position: Int): Fragment {
        // Create and return a new instance of your fragment
        return fragments[position].second
    }

    private fun addFragment(str: String): Fragment{
        val bundle = Bundle()
        bundle.putString("breedName",str)
        val fragment = HomeViewPagerFragment.newInstance(bundle)
        return fragment
    }

    override fun getItemCount(): Int {
        return fragments.size
    }
}