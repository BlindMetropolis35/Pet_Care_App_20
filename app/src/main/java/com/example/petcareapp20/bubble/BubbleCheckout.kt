package com.example.petcareapp20.bubble

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.petcareapp20.R
import com.example.petcareapp20.databinding.ActivityBubbleCheckoutBinding

class BubbleCheckout : AppCompatActivity() {
    private lateinit var binding: ActivityBubbleCheckoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBubbleCheckoutBinding.inflate(layoutInflater)

        //memanggil layout dengan menggunakan binding.root
        setContentView(binding.root)

        supportActionBar?.hide()

        val kembali1=binding.kembali1
        kembali1.setOnClickListener {
            val intent = Intent(this, SearchHealth::class.java)
            startActivity(intent)

        }
        val butonbayar=binding.butonbayar
        butonbayar.setOnClickListener {
            val intent = Intent(this, ContinuePay::class.java)
            startActivity(intent)

        }
    }
}