package com.example.dimplebooks.UI.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.dimplebooks.UI.mainWelcomeScreen

class welcomeScreenAdapter(activity : mainWelcomeScreen,
                           private val fragments:List<Fragment> )
    : FragmentStateAdapter(activity)
{
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}