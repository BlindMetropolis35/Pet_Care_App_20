package com.example.petcareapp20.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.petcareapp20.R
import com.example.petcareapp20.mainhome.HomeActivity
import com.example.petcareapp20.onboarding.ViewPagerAdapter
import com.example.petcareapp20.onboarding.screens.FirstScreen
import com.example.petcareapp20.onboarding.screens.SecondScreen
import com.example.petcareapp20.onboarding.screens.ThirdScreen


class ViewPagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_pager, container, false)

        val fragmentList = arrayListOf<Fragment>(
            FirstScreen(),
            SecondScreen(),
            ThirdScreen()
        )

        val adapter = ViewPagerAdapter(fragmentList,requireActivity().supportFragmentManager, lifecycle)

        val viewPager=view.findViewById<ViewPager2>(R.id.viewPager)
        viewPager.adapter = adapter

        return view
    }
}