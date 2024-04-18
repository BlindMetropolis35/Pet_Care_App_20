package com.example.petcareapp20.mainhome.ui.drug

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.petcareapp20.ComingSoon
import com.example.petcareapp20.R
import com.example.petcareapp20.drug.DrugCheckout
import com.example.petcareapp20.retrofit_sec.DrugCallList
import com.example.petcareapp20.retrofit_sec.DrugCardInfo
import com.example.petcareapp20.retrofit_sec.DrugDetailService
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.jvm.java

class DrugDetailed : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_drug_detailed)

        val drug_id = intent.getStringExtra("drugId")

        if (drug_id != null) {
            fetchDrugDetails(drug_id)
        } else {
            // Handle error
            Toast.makeText(this, "Error: Drug not found", Toast.LENGTH_SHORT).show()
        }

        val backdrugrec = findViewById<ImageButton>(R.id.backdrugrec)
        backdrugrec.setOnClickListener {
            val intent = Intent(this@DrugDetailed, DrugFragment::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        val marqueeTextView = findViewById<TextView>(R.id.my_marquee_texts)
        marqueeTextView.isSelected = true
        marqueeTextView.requestFocus()

        val buy_now=findViewById<MaterialButton>(R.id.buy_now)
        buy_now.setOnClickListener{
            val intent= Intent(this, DrugCheckout::class.java)
            intent.putExtra("drugId",drug_id)
            startActivity(intent)
            finish()
        }

        val add_cart=findViewById<MaterialButton>(R.id.add_cart)
        add_cart.setOnClickListener{
            val intent= Intent(this, ComingSoon::class.java)
            startActivity(intent)
        }
    }

    private fun fetchDrugDetails(doctorId: String) {
        val call: Call<DrugCallList> = DrugDetailService.drugInstance.getDrugInfo(1, "us")
        call.enqueue(object : Callback<DrugCallList> {
            override fun onResponse(call: Call<DrugCallList>, response: Response<DrugCallList>) {
                if (response.isSuccessful) {
                    val drugList = response.body()
                    if (drugList != null) {
                        val matchingDrug = drugList.druginfos.find { it._id == doctorId }
                        if (matchingDrug != null) {
                            updateUI(matchingDrug)
                        } else {
                            // Handle error
                            Toast.makeText(
                                this@DrugDetailed,
                                "Error: Doctor not found",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        // Handle error: empty response
                        Toast.makeText(
                            this@DrugDetailed,
                            "Error: No data found",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    // Handle error: API call failed
                    Toast.makeText(this@DrugDetailed, "Error: Request failed", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<DrugCallList>, t: Throwable) {
                // Handle network or other errors
                Toast.makeText(this@DrugDetailed, "Error: Network error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateUI(matchingDrug: DrugCardInfo) {

        val drug_image = findViewById<ImageView>(R.id.drug_image)
        Glide.with(this).load(matchingDrug.urlToImage).into(drug_image)

        val drug_name = findViewById<TextView>(R.id.drug_name)
        drug_name.text = matchingDrug.drug_name

        val drug_price = findViewById<TextView>(R.id.drug_price)
        drug_price.text = matchingDrug.drug_price

        val drug_desc = findViewById<TextView>(R.id.drug_desc)
        drug_desc.text = matchingDrug.description
    }
}