package com.example.petcareapp20.mainhome.ui.drug

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.petcareapp20.R
import com.example.petcareapp20.databinding.FragmentDrugBinding
import com.example.petcareapp20.drug.BlackDrug

class DrugFragment : Fragment() {

    private var _binding: FragmentDrugBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDrugBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val apotik2=binding.apotik2
        apotik2.setOnClickListener {
            val intent = Intent(activity, BlackDrug::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}