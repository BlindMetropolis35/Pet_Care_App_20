package com.example.petcareapp20.donation

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.petcareapp20.R
import com.example.petcareapp20.databinding.ActivityPickNominalSuccessBinding

class PickNominalSuccess : AppCompatActivity() {

    private lateinit var binding: ActivityPickNominalSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pick_nominal_success)
        binding = ActivityPickNominalSuccessBinding.inflate(layoutInflater)

        //memanggil layout dengan menggunakan binding.root
        setContentView(binding.root)

        supportActionBar?.hide()

        val btndonasi=binding.btndonasi
        btndonasi.setOnClickListener {
            val intent = Intent(this, DonationSuccess::class.java)
            startActivity(intent)

        }
    }
}