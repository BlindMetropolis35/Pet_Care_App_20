package com.example.petcareapp20.donation

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.petcareapp20.R
import com.example.petcareapp20.databinding.ActivityDonationSuccessBinding
import com.example.petcareapp20.mainhome.HomeActivity
import com.example.petcareapp20.mainhome.ui.home.HomeFragment

class DonationSuccess : AppCompatActivity() {

    private lateinit var binding: ActivityDonationSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_donation_success)
        binding = ActivityDonationSuccessBinding.inflate(layoutInflater)

        //memanggil layout dengan menggunakan binding.root
        setContentView(binding.root)

        supportActionBar?.hide()

        val kembali3=binding.kembali3
        kembali3.setOnClickListener {
            val intent = Intent(this, PayDonation::class.java)
            startActivity(intent)

        }
        val backberanda=binding.backberanda
        backberanda.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)

        }
    }
}