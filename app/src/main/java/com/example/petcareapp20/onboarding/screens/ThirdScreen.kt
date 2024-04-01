package com.example.petcareapp20.onboarding.screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.petcareapp20.LoginActivity
import com.example.petcareapp20.R
import com.example.petcareapp20.RegisterActivity
import com.example.petcareapp20.mainhome.HomeActivity
import com.google.android.material.button.MaterialButton

class ThirdScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_third_screen, container, false)

        val Login=view.findViewById<MaterialButton>(R.id.Login)
        Login.setOnClickListener {
            val intent= Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
        }

        val Register=view.findViewById<MaterialButton>(R.id.Register)
        Register.setOnClickListener {
            val intent = Intent(requireActivity(), RegisterActivity::class.java)
            startActivity(intent)
        }

        return view
    }

}