package com.example.petcareapp20.mainhome.ui.doctor

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.petcareapp20.BuildConfig
import com.example.petcareapp20.HomeService.MainDoctorLists
import com.example.petcareapp20.R
import com.example.petcareapp20.retrofit.DoctorCallList
import com.example.petcareapp20.retrofit.DoctorCardInfo
import com.example.petcareapp20.retrofit.DoctorDetailService
import com.google.android.material.button.MaterialButton
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService
import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoSendCallInvitationButton
import com.zegocloud.uikit.service.defines.ZegoUIKitUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar
import java.util.Locale
import kotlin.random.Random

class DoctorFragDetailed : AppCompatActivity() {

    private var selectedButtonId: Int = -1
    val appID= BuildConfig.APP_ID
    val appSign= BuildConfig.APP_SIGN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_doctor_frag_detailed)

        val doctorId = intent.getStringExtra("doctorId")
        if (doctorId != null) {
            fetchDoctorDetails(doctorId)
        } else {
            // Handle error
            Toast.makeText(this, "Error: Doctor ID not found", Toast.LENGTH_SHORT).show()
        }

        val calendarView = findViewById<View>(R.id.calendar_view) as CalendarView
        val today: Calendar = Calendar.getInstance()
        calendarView.minDate = today.getTimeInMillis()
        today.add(Calendar.DAY_OF_MONTH, 6) // Add 5 days to today's date
        calendarView.maxDate = today.getTimeInMillis()

        //set time
        val button4 = findViewById<MaterialButton>(R.id.button4)
        val button5 = findViewById<MaterialButton>(R.id.button5)
        val button6 = findViewById<MaterialButton>(R.id.button6)

        val buttons2 = listOf(button4, button5, button6)

        buttons2.forEach { button ->
            button.setOnClickListener {
                val buttonId = button.id
                if (buttonId != selectedButtonId) {
                    setSelectedButton(buttonId)
                }
            }
        }

        val marqueeTextView = findViewById<TextView>(R.id.my_marquee_text)
        marqueeTextView.isSelected = true
        marqueeTextView.requestFocus()

        //
        //zego cloud
        val userID: String = generateUserID()
        val userName: String = buildString {
            append(userID + "_")
            append(Build.MANUFACTURER.lowercase(Locale.getDefault()).toString())
        }
        val callID: String = "test_call_id"

        if (userID != null) {
            if (userName != null) {
                initCallInviteService(appID, appSign, userID, userName)
            }
        }

//        initVoiceButton()
//        initVideoButton()

    }

    private fun generateUserID(): String {
        val builder = StringBuilder()
        val random = Random(8)
        while (builder.length < 15) {
            val nextInt: Int = random.nextInt(10)
            if (builder.length == 0 && nextInt == 0) {
                continue
            }
            builder.append(nextInt)
        }
        return builder.toString()
    }

    private fun fetchDoctorDetails(doctorId: String) {
        val call: Call<DoctorCallList> = DoctorDetailService.doctorInstance.getVetInfo(1,"us")
        call.enqueue(object : Callback<DoctorCallList> {
            override fun onResponse(call: Call<DoctorCallList>, response: Response<DoctorCallList>) {
                if (response.isSuccessful) {
                    val doctorList = response.body()
                    if (doctorList != null) {
                        val matchingDoctor = doctorList.vetinfos.find { it._id == doctorId }
                        if (matchingDoctor != null) {
                            // Update UI with matchingDoctor data
                            updateUI(matchingDoctor)
                            initVoiceButton(matchingDoctor)
                            initVideoButton(matchingDoctor)
                        } else {
                            // Handle error
                            Toast.makeText(this@DoctorFragDetailed, "Error: Doctor not found", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // Handle error: empty response
                        Toast.makeText(this@DoctorFragDetailed, "Error: No data found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Handle error: API call failed
                    Toast.makeText(this@DoctorFragDetailed, "Error: Request failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DoctorCallList>, t: Throwable) {
                // Handle network or other errors
                Toast.makeText(this@DoctorFragDetailed, "Error: Network error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateUI(matchingDoctor: DoctorCardInfo) {

        val vet_image = findViewById<ImageView>(R.id.vet_imaged)
        Glide.with(this).load(matchingDoctor.urlToImage).into(vet_image)

        val vet_name = findViewById<TextView>(R.id.vet_named)
        vet_name.text = matchingDoctor.vet_name

        val clinic_name = findViewById<TextView>(R.id.clinic_named)
        clinic_name.text = matchingDoctor.clinic_name

        val clinic_address = findViewById<TextView>(R.id.clinic_addressd)
        clinic_address.text = matchingDoctor.clinic_address

//        phone hidden
        val visiblePart = findViewById<TextView>(R.id.clinic_phone_visible)
        val maskedPart = findViewById<TextView>(R.id.clinic_phone_masked)

        val phoneNumber = matchingDoctor.clinic_phone
        val visibleNumber = phoneNumber.substring(0, phoneNumber.length / 2)

        visiblePart.text = visibleNumber
        maskedPart.text = "xxxxx"

        val gender = findViewById<TextView>(R.id.genderd)
        gender.text = matchingDoctor.gender

        val service_years = findViewById<TextView>(R.id.service_yearsd)
        service_years.text = matchingDoctor.service_years

        val alumni_of_uni = findViewById<TextView>(R.id.alumni_of_unid)
        alumni_of_uni.text = matchingDoctor.alumni_of_uni

        val price = findViewById<TextView>(R.id.priced)
        price.text = matchingDoctor.price

        val vet_age = findViewById<TextView>(R.id.vet_aged)
        vet_age.text = matchingDoctor.vet_age

        /*val chat_price = findViewById<TextView>(R.id.chat_priced)
        chat_price.text = matchingDoctor.chat_price

        val video_price = findViewById<TextView>(R.id.video_priced)
        video_price.text = matchingDoctor.video_price*/

        val vet_rating = findViewById<TextView>(R.id.vet_rating)
        vet_rating.text = matchingDoctor.vet_rating
    }

    private fun setSelectedButton(buttonId: Int) {
        val previousButton = findViewById<Button>(selectedButtonId)
        val newButton = findViewById<Button>(buttonId)

        previousButton?.isSelected = false
        newButton.isSelected = true
        selectedButtonId = buttonId
    }

    private fun initCallInviteService(appID: Long, appSign: String, userID: String, userName: String) {
        val callInvitationConfig = ZegoUIKitPrebuiltCallInvitationConfig()
        ZegoUIKitPrebuiltCallInvitationService.init(
            application, appID, appSign, userID, userName, callInvitationConfig
        )
    }

    private fun initVoiceButton(matchingDoctor: DoctorCardInfo) {
        val newVoiceCall = findViewById<ZegoSendCallInvitationButton>(R.id.chatButton)
        newVoiceCall.setIsVideoCall(false)
        newVoiceCall.setOnClickListener(View.OnClickListener {
            val targetUserID: String = matchingDoctor._id
            val users = targetUserID.split(",").map { userID ->
                ZegoUIKitUser(userID)
            }
            newVoiceCall.setInvitees(users)
        })
    }

    private fun initVideoButton(matchingDoctor: DoctorCardInfo) {
        val newVideoCall = findViewById<ZegoSendCallInvitationButton>(R.id.videoButton)
        newVideoCall.setIsVideoCall(true)
        newVideoCall.setOnClickListener(View.OnClickListener {
            val targetUserID: String = matchingDoctor._id
            val users = targetUserID.split(",").map { userID ->
                ZegoUIKitUser(userID)
            }
            newVideoCall.setInvitees(users)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        ZegoUIKitPrebuiltCallInvitationService.unInit()
    }
}