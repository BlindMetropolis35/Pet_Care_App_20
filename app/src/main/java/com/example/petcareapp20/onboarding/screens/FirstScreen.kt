package com.example.petcareapp20.onboarding.screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.viewpager2.widget.ViewPager2
import com.example.petcareapp20.LoginActivity
import com.example.petcareapp20.R

class FirstScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_first_screen, container, false)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)

        val skipbutton1=view.findViewById<Button>(R.id.skipbutton1)
        skipbutton1.setOnClickListener {
            val intent= Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }

        val next1=view.findViewById<Button>(R.id.next1)
        next1.setOnClickListener {
            viewPager?.currentItem = 1
        }

        return view
    }
}