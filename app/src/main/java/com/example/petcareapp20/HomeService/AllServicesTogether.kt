package com.example.petcareapp20.HomeService

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.petcareapp20.R
import com.example.petcareapp20.mainhome.HomeActivity

class AllServicesTogether : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_all_services_together)

        val imageView2=findViewById<ImageView>(R.id.imageView2)
        imageView2.setOnClickListener {
            val intent= Intent(this,HomeActivity::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        val doctor1_cardview=findViewById<CardView>(R.id.doctor1_cardview)
        doctor1_cardview.setOnClickListener{
            val intent=Intent(this,MainDoctorLists::class.java)
            startActivity(intent)
        }
    }
}