package com.example.petcareapp20.drug

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.petcareapp20.R
import com.example.petcareapp20.databinding.ActivityNextPaymentBinding

class NextPayment : AppCompatActivity() {
    private lateinit var binding: ActivityNextPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNextPaymentBinding.inflate(layoutInflater)

        //memanggil layout dengan menggunakan binding.root
        setContentView(binding.root)

        supportActionBar?.hide()

        val btnlanjtpay=binding.btnlanjtpay
        btnlanjtpay.setOnClickListener {
            val intent = Intent(this, DrugTxSuccessful::class.java)
            startActivity(intent)
        }
    }
}