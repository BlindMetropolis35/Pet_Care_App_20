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

    private var _binding: FragmentDonationBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val notificationsViewModel = ViewModelProvider(this)[DonationViewModel::class.java]

        _binding = FragmentDonationBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btndonasiskrng=binding.btndonasiskrng
        btndonasiskrng.setOnClickListener {
            val intent = Intent(activity, PayDonation::class.java)
            startActivity(intent)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}