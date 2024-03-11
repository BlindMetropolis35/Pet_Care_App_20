package com.example.petcareapp20.HomeService

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.ui.text.toLowerCase
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.petcareapp20.R
import com.example.petcareapp20.doctor.DoctorDetailed
import com.example.petcareapp20.retrofit.DoctorCardInfo

class MainDoctorListAdapter(val context: Context, var vetinfos: List<DoctorCardInfo>) : RecyclerView.Adapter<MainDoctorListAdapter.DoctorCardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorCardViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.itemdoctorcard, parent, false)
        return DoctorCardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return vetinfos.size
    }

    override fun onBindViewHolder(holder: DoctorCardViewHolder, position: Int) {
        val vetinfos = vetinfos[position]
        Glide.with(context).load(vetinfos.urlToImage).into(holder.vet_image)
        holder.vet_name.text = vetinfos.vet_name
        holder.clinic_name.text = vetinfos.clinic_name
        holder.vet_age.text = vetinfos.vet_age
        holder.clinic_phone.text = vetinfos.clinic_phone
        holder.gender.text = vetinfos.gender
        holder.vet_name.text = vetinfos.vet_name
        holder.specialization.text = vetinfos.specialization
        holder.clinic_address.text = vetinfos.clinic_address

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DoctorDetailed::class.java)
            intent.putExtra("doctorId", vetinfos._id)
            context.startActivity(intent)
        }
    }

    inner class DoctorCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var vet_image = itemView.findViewById<ImageView>(R.id.vet_image)
        var vet_name = itemView.findViewById<TextView>(R.id.vet_name)
        var clinic_name = itemView.findViewById<TextView>(R.id.clinic_name)
        var vet_age = itemView.findViewById<TextView>(R.id.vet_age)
        var gender = itemView.findViewById<TextView>(R.id.vet_gender)
        var clinic_address = itemView.findViewById<TextView>(R.id.clinic_address)
        var clinic_phone = itemView.findViewById<TextView>(R.id.clinic_phone)
        var specialization = itemView.findViewById<TextView>(R.id.specialization)
    }

    fun filter(query: String) {
        val filteredList = mutableListOf<DoctorCardInfo>()
        for (item in vetinfos) {
            val searchText = query.toLowerCase()
            if (item.vet_name.toLowerCase().contains(searchText) ||
                item.specialization.toLowerCase().contains(searchText) ||
                item.clinic_name.toLowerCase().contains(searchText) ||
                item.clinic_address.toLowerCase().contains(searchText)) {
                filteredList.add(item)
            }
        }
        vetinfos = filteredList
        notifyDataSetChanged()
    }
}