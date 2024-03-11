package com.example.petcareapp20.drug

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.petcareapp20.R
import com.example.petcareapp20.databinding.ActivityDetailDrugBinding

class DetailDrug : AppCompatActivity() {

    private lateinit var binding: ActivityDetailDrugBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDrugBinding.inflate(layoutInflater)

        //memanggil layout dengan menggunakan binding.root
        setContentView(binding.root)

        supportActionBar?.hide()

        val back=binding.back
        back.setOnClickListener {
            val intent = Intent(this, DetailDrug::class.java)
            startActivity(intent)

        }
        val butonbayar=binding.butonbayar
        butonbayar.setOnClickListener {
            val intent = Intent(this, DrugCheckout::class.java)
            startActivity(intent)

        }
    }
}