package com.example.petcareapp20.mainhome.ui.drug

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.petcareapp20.BuildConfig
import com.example.petcareapp20.R
import com.example.petcareapp20.mainhome.ui.doctor.DoctorFragDetailed
import com.example.petcareapp20.retrofit.DoctorCardInfo
import com.example.petcareapp20.retrofit_sec.DrugCardInfo
import com.google.android.material.button.MaterialButton
import java.util.Locale
import kotlin.random.Random

class DrugFragmentAdapter(val context: Context, var druginfos: List<DrugCardInfo>) : RecyclerView.Adapter<DrugFragmentAdapter.DrugCardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrugCardViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_drug_frag, parent, false)

        return DrugCardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return druginfos.size
    }

    override fun onBindViewHolder(holder: DrugCardViewHolder, position: Int) {
        val druginfos = druginfos[position]
        Glide.with(context).load(druginfos.urlToImage).into(holder.drug_image)
        holder.drug_name.text = druginfos.drug_name
        holder.drug_price.text = druginfos.drug_price

        holder.drug_buy.setOnClickListener {
            val intent = Intent(context, DrugDetailed::class.java)
            intent.putExtra("drugId", druginfos._id)
            context.startActivity(intent)
        }
    }

    inner class DrugCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var drug_image = itemView.findViewById<ImageView>(R.id.drug_image)
        var drug_name = itemView.findViewById<TextView>(R.id.drug_name)
        var drug_price = itemView.findViewById<TextView>(R.id.drug_price)
        var drug_buy = itemView.findViewById<MaterialButton>(R.id.drug_buy)
    }
}