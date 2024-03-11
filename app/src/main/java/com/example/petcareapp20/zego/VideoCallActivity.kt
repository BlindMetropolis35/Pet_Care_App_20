package com.example.petcareapp20.zego

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.petcareapp20.R
import com.google.android.material.textfield.TextInputLayout
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService
import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoSendCallInvitationButton
import com.zegocloud.uikit.service.defines.ZegoUIKitUser

class VideoCallActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_video_call)

        val yourUserID = findViewById<TextView>(R.id.your_user_id)
        val yourUserName = findViewById<TextView>(R.id.your_user_name)
        val userID = intent.getStringExtra("userID")
        val userName = intent.getStringExtra("userName")

        yourUserID.text = "Your User ID: $userID"
        yourUserName.text = "Your User Name: $userName"

        val appID: Long = 1202808636
        val appSign: String = "075249572970ba3a5b86744d68f540e671ba491bf88a42a561c5000ed31fdfd9"
        if (userID != null) {
            if (userName != null) {
                initCallInviteService(appID, appSign, userID, userName)
            }
        }

        initVoiceButton()
        initVideoButton()

    }

    private fun initCallInviteService(
        appID: Long,
        appSign: String,
        userID: String,
        userName: String
    ) {
        val callInvitationConfig = ZegoUIKitPrebuiltCallInvitationConfig()
        ZegoUIKitPrebuiltCallInvitationService.init(
            application, appID, appSign, userID, userName, callInvitationConfig
        )
    }

    private fun initVideoButton() {
        val newVideoCall = findViewById<ZegoSendCallInvitationButton>(R.id.new_video_call)
        newVideoCall.setIsVideoCall(true)
        newVideoCall.setOnClickListener(View.OnClickListener {
            val inputLayout = findViewById<TextInputLayout>(R.id.target_user_id)
            val targetUserID = inputLayout.editText?.text?.toString() ?: ""
            val users = targetUserID.split(",").map { userID ->
                val userName: String = userID + "_name"
                ZegoUIKitUser(userID, userName)
            }
            newVideoCall.setInvitees(users)
        })
    }


    private fun initVoiceButton() {
        val newVoiceCall = findViewById<ZegoSendCallInvitationButton>(R.id.new_voice_call)
        newVoiceCall.setIsVideoCall(false)
        newVoiceCall.setOnClickListener(View.OnClickListener {
            val inputLayout = findViewById<TextInputLayout>(R.id.target_user_id)
            val targetUserID = inputLayout.editText?.text?.toString() ?:""
            val users = targetUserID.split(",").map { userID ->
                val userName: String = userID + "_name"
                ZegoUIKitUser(userID, userName)
            }
            newVoiceCall.setInvitees(users)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        ZegoUIKitPrebuiltCallInvitationService.unInit()
    }
}