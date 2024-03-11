package com.example.petcareapp20.bubble

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.petcareapp20.R
import com.example.petcareapp20.databinding.ActivityTxSuccessBinding
import com.example.petcareapp20.mainhome.HomeActivity
import com.example.petcareapp20.mainhome.ui.home.HomeFragment

class TxSuccess : AppCompatActivity() {
    private lateinit var binding: ActivityTxSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTxSuccessBinding.inflate(layoutInflater)

        //memanggil layout dengan menggunakan binding.root
        setContentView(binding.root)

        supportActionBar?.hide()

        val kembali3=binding.kembali3
        kembali3.setOnClickListener {
            val intent = Intent(this, SearchHealth::class.java)
            startActivity(intent)

        }

        val backberanda=binding.backberanda
        backberanda.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)

        }
    }
}