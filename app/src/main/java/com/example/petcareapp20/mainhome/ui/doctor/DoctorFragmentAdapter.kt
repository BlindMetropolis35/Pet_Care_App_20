package com.example.petcareapp20.mainhome.ui.doctor

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.compose.ui.text.toLowerCase
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elvishew.xlog.XLog.init
import com.example.petcareapp20.R
import com.example.petcareapp20.doctor.DoctorDetailed
import com.example.petcareapp20.retrofit.DoctorCardInfo
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService
import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoSendCallInvitationButton
import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoVideoCallButton
import com.zegocloud.uikit.service.defines.ZegoUIKitUser
import java.util.Locale
import kotlin.random.Random

class DoctorFragmentAdapter(val context: Context, var vetinfos: List<DoctorCardInfo>) : RecyclerView.Adapter<DoctorFragmentAdapter.DoctorCardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorCardViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_doc_frag, parent, false)
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

        holder.doctorDetailed.setOnClickListener {
            val intent = Intent(context, DoctorDetailed::class.java)
            intent.putExtra("doctorId", vetinfos._id)
            context.startActivity(intent)
        }

        holder.chatButton.setOnClickListener {
            val intent = Intent(context, DoctorDetailed::class.java)
            intent.putExtra("doctorId", vetinfos._id)
            context.startActivity(intent)
        }

        holder.videoButton.setOnClickListener {
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
        var doctorDetailed = itemView.findViewById<LinearLayout>(R.id.doctorDetailed)
        var chatButton = itemView.findViewById<Button>(R.id.chatButton)
        var videoButton = itemView.findViewById<Button>(R.id.videoButton)
    }
}