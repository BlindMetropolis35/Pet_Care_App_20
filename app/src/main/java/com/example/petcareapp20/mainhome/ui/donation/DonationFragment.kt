package com.example.petcareapp20.mainhome.ui.donation

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.petcareapp20.R
import com.example.petcareapp20.databinding.FragmentDonationBinding
import com.example.petcareapp20.donation.PayDonation

class DonationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_donation, container, false)

        return view
    }
}