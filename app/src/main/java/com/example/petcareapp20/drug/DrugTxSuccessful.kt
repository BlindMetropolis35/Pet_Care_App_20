package com.example.petcareapp20.drug

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.petcareapp20.ComingSoon
import com.example.petcareapp20.R
import com.example.petcareapp20.mainhome.ui.drug.DrugDetailed
import com.example.petcareapp20.mainhome.ui.drug.DrugFragment
import com.google.android.material.button.MaterialButton
import kotlin.random.Random

class DrugTxSuccessful : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_drug_tx_successful)

        val drugId=intent.getStringExtra("drugId")

        val backdrugs=findViewById<ImageButton>(R.id.backdrugs)
        backdrugs.setOnClickListener{
            val intent= Intent(this@DrugTxSuccessful, DrugDetailed::class.java)
            intent.putExtra("drugId",drugId)
            startActivity(intent)
            finish()
        }

        val orderIdGen=generateUserID()
        updateUI(orderIdGen)

        val back_to_home=findViewById<MaterialButton>(R.id.back_to_home)
        back_to_home.setOnClickListener{
            val intent= Intent(this, DrugFragment::class.java)
            intent.flags= Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        val goto_orders=findViewById<MaterialButton>(R.id.goto_orders)
        goto_orders.setOnClickListener{
            val intent= Intent(this, ComingSoon::class.java)
            startActivity(intent)
        }
    }

    private fun generateUserID(): String {
        val builder = StringBuilder()
        val random = Random(8)
        while (builder.length < 15) {
            val nextInt: Int = random.nextInt(10)
            if (builder.isEmpty() && nextInt == 0) {
                continue
            }
            builder.append(nextInt)
        }
        return builder.toString()
    }

    fun updateUI(orderIdGen: String) {
        val order_id=findViewById<TextView>(R.id.order_id)
        order_id.text=orderIdGen
    }
}