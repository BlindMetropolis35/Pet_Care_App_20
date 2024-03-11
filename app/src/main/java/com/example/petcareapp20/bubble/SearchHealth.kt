package com.example.petcareapp20.bubble

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.petcareapp20.R
import com.example.petcareapp20.databinding.ActivitySearchHealthBinding
import com.example.petcareapp20.mainhome.HomeActivity
import com.example.petcareapp20.mainhome.ui.home.HomeFragment

class SearchHealth : AppCompatActivity() {
    private lateinit var binding: ActivitySearchHealthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchHealthBinding.inflate(layoutInflater)

        //memanggil layout dengan menggunakan binding.root
        setContentView(binding.root)

        supportActionBar?.hide()

        val back=binding.back
        back.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        val rs1=binding.rs1
        rs1.setOnClickListener {
            val intent = Intent(this, SetSchedule::class.java)
            startActivity(intent)

        }
    }
}