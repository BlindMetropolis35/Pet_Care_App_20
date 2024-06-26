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
import com.google.android.material.button.MaterialButton

class SecondScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_second_screen, container, false)

        val viewPager =  activity?.findViewById<ViewPager2>(R.id.viewPager)

        val skipbutton2=view.findViewById<MaterialButton>(R.id.skipbutton2)
        skipbutton2.setOnClickListener {
            val intent=Intent(activity,LoginActivity::class.java)
            startActivity(intent)
        }

        val next2=view.findViewById<MaterialButton>(R.id.next2)
        next2.setOnClickListener {
            viewPager?.currentItem = 2
        }

        return view
    }
}