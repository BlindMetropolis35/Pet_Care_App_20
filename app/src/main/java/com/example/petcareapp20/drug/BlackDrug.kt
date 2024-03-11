package com.example.petcareapp20.drug

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.petcareapp20.R
import com.example.petcareapp20.databinding.ActivityBlackDrugBinding
import com.example.petcareapp20.mainhome.HomeActivity
import com.example.petcareapp20.mainhome.ui.home.HomeFragment

class BlackDrug : AppCompatActivity() {
    private lateinit var binding: ActivityBlackDrugBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBlackDrugBinding.inflate(layoutInflater)

        //memanggil layout dengan menggunakan binding.root
        setContentView(binding.root)

        supportActionBar?.hide()

        val back=binding.back
        back.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)

        }
        val amoxilin=binding.amoxilin
        amoxilin.setOnClickListener {
            val intent = Intent(this, DetailDrug::class.java)
            startActivity(intent)

        }
    }
}