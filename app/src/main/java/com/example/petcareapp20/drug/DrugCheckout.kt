package com.example.petcareapp20.drug

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.trimmedLength
import com.bumptech.glide.Glide
import com.example.petcareapp20.R
import com.example.petcareapp20.mainhome.ui.drug.DrugDetailed
import com.example.petcareapp20.retrofit_sec.DrugCallList
import com.example.petcareapp20.retrofit_sec.DrugCardInfo
import com.example.petcareapp20.retrofit_sec.DrugDetailService
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DrugCheckout : AppCompatActivity() {

    private var qtyCount:Int= 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_drug_checkout)

        val drug_id=intent.getStringExtra("drugId")

        if (drug_id != null) {
            fetchDrugDetails(drug_id)
        } else {
            // Handle error
            Toast.makeText(this, "Error: Drug not found", Toast.LENGTH_SHORT).show()
        }

        val back_button11=findViewById<ImageButton>(R.id.back_button11)
        back_button11.setOnClickListener{
            val intent= Intent(this, DrugDetailed::class.java)
            intent.putExtra("drugId",drug_id)
            startActivity(intent)
            finish()
        }

        val qtyDecrementButton  = findViewById<MaterialButton>(R.id.qty_dec )
        val qtyIncrementButton =findViewById<MaterialButton>(R.id.qty_inc )

        updateQuantityButtons(qtyCount)

        qtyDecrementButton.setOnClickListener {
            if (qtyCount > 1) {
                qtyCount--
                updateQuantityButtons(qtyCount)
                updateOrderTotal()
            }
        }

        qtyIncrementButton.setOnClickListener {
            if (qtyCount < 5) {
                qtyCount++
                updateQuantityButtons(qtyCount)
                updateOrderTotal()
            }
        }

        val proceed_payment = findViewById<MaterialButton>(R.id.proceed_payment)
        proceed_payment.setOnClickListener {
            if (qtyCount>0) {
                val intent = Intent(this, DrugTxSuccessful::class.java)
                intent.putExtra("drugId", drug_id)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this,"Min. qty should be 1", Toast.LENGTH_SHORT).show()
            }
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
                            updateOrderTotal()
                        } else {
                            // Handle error
                            Toast.makeText(
                                this@DrugCheckout,
                                "Error: Doctor not found",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        // Handle error: empty response
                        Toast.makeText(
                            this@DrugCheckout,
                            "Error: No data found",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    // Handle error: API call failed
                    Toast.makeText(this@DrugCheckout, "Error: Request failed", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<DrugCallList>, t: Throwable) {
                // Handle network or other errors
                Toast.makeText(this@DrugCheckout, "Error: Network error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateUI(matchingDrug:DrugCardInfo){
        val drug_image = findViewById<ImageView>(R.id.drugImg)
        Glide.with(this).load(matchingDrug.urlToImage).into(drug_image)

        val drug_name = findViewById<TextView>(R.id.drugName)
        drug_name.text = matchingDrug.drug_name

        val drug_price = findViewById<TextView>(R.id.drug_price_init)
        drug_price.text = matchingDrug.drug_price

    }

    private fun updateOrderTotal() {
        val drug_price_init=findViewById<TextView>(R.id.drug_price_init).text.toString()
        val drug_price=drug_price_init.removeRange(0,1).toFloat()

        val drug_charges=findViewById<TextView>(R.id.drug_charges).text.toString()
        val charges=drug_charges.removeRange(0,1).toFloat()

        val qtyCount=findViewById<TextView>(R.id.qty_count).text.toString()
        val new_qty=qtyCount.toInt()

        val total_order=(drug_price + charges) * new_qty

        val new_total= StringBuilder()
            .append("$")
            .append(total_order)

        val order_total_price=findViewById<TextView>(R.id.order_total_price)
        order_total_price.text=new_total.trim()
    }

    override fun onDestroy() {
        super.onDestroy()
        qtyCount==1
    }

    private fun updateQuantityButtons(currentQty: Int) {
        val qtyDecrementButton  = findViewById<MaterialButton>(R.id.qty_dec )
        val qtyIncrementButton =findViewById<MaterialButton>(R.id.qty_inc )
        val qty_count=findViewById<TextView>(R.id.qty_count)

        qtyDecrementButton.isEnabled = currentQty > 1
        qtyIncrementButton.isEnabled = currentQty < 5

        qty_count.text = currentQty.toString().trim()
    }
}