package com.example.petcareapp20.doctormenus

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.petcareapp20.R
import com.example.petcareapp20.mainhome.HomeActivity
import com.example.petcareapp20.mainhome.ui.home.HomeFragment

class Doctor : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor)

        supportActionBar?.hide()

        val backabu=findViewById<ImageView>(R.id.backabu)
        backabu.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)

        }
    }
}