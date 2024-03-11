package com.example.petcareapp20.bubble

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.petcareapp20.R
import com.example.petcareapp20.databinding.ActivityContinuePayBinding

class ContinuePay : AppCompatActivity() {
    private lateinit var binding: ActivityContinuePayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContinuePayBinding.inflate(layoutInflater)

        //memanggil layout dengan menggunakan binding.root
        setContentView(binding.root)

        supportActionBar?.hide()

        val kembali2=binding.kembali2
        kembali2.setOnClickListener {
            val intent = Intent(this, BubbleCheckout::class.java)
            startActivity(intent)

        }

        val btnlanjutbayar=binding.btnlanjutbayar
        btnlanjutbayar.setOnClickListener {
            val intent = Intent(this, TxSuccess::class.java)
            startActivity(intent)

        }
    }
}