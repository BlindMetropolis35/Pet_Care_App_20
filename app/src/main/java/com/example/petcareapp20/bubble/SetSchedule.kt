package com.example.petcareapp20.bubble

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.petcareapp20.R
import com.example.petcareapp20.databinding.ActivitySetScheduleBinding

class SetSchedule : AppCompatActivity() {
    private lateinit var binding: ActivitySetScheduleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetScheduleBinding.inflate(layoutInflater)

        //memanggil layout dengan menggunakan binding.root
        setContentView(binding.root)

        supportActionBar?.hide()

        val backabuabu=binding.backabuabu
        backabuabu.setOnClickListener {
            val intent = Intent(this, SearchHealth::class.java)
            startActivity(intent)

        }
        val btn_pesan=binding.btnPesan
        btn_pesan.setOnClickListener {
            val intent = Intent(this, BubbleCheckout::class.java)
            startActivity(intent)

        }
    }
}