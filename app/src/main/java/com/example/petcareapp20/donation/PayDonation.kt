package com.example.petcareapp20.donation

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.petcareapp20.R
import com.example.petcareapp20.databinding.ActivityPayDonationBinding
import com.example.petcareapp20.mainhome.HomeActivity
import com.example.petcareapp20.mainhome.ui.home.HomeFragment

class PayDonation : AppCompatActivity() {

    private lateinit var binding: ActivityPayDonationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pay_donation)
        binding = ActivityPayDonationBinding.inflate(layoutInflater)

        setContentView(binding.root)

        supportActionBar?.hide()

        val backk=binding.backk
        backk.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)

        }
        val nominal=binding.nominal
        nominal.setOnClickListener {
            val intent = Intent(this, PickNominalSuccess::class.java)
            startActivity(intent)
        }
    }
}