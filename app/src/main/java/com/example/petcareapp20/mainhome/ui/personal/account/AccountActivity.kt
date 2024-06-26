package com.example.petcareapp20.mainhome.ui.personal.account

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import com.example.petcareapp20.R
import com.example.petcareapp20.mainhome.ui.personal.PersonalFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import com.hbb20.CountryCodePicker
import java.util.concurrent.TimeUnit

class AccountActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private var verificationId: String? = null
    private var verNewMail= StringBuilder()
    private var editYourPhone= StringBuilder()
    private val REQUEST_IMAGE_PICKER = 200
    private val REQUEST_READ_STORAGE_PERMISSION = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_account)

        firebaseAuth = FirebaseAuth.getInstance()
        val user = firebaseAuth.currentUser

        val backtopersonal=findViewById<ImageButton>(R.id.backtopersonal)
        backtopersonal.setOnClickListener{
            val intent = Intent(this, PersonalFragment::class.java)
            startActivity(intent)
            finish()
        }

        //profile photo

        val profile_img_layout = findViewById<LinearLayout>(R.id.profile_img_layout)
        profile_img_layout.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_READ_STORAGE_PERMISSION)
            } else {
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, REQUEST_IMAGE_PICKER)
            }
        }

        //Name

        val editFirstName = findViewById<TextInputEditText>(R.id.editFirstName)
        val editLastName = findViewById<TextInputEditText>(R.id.editLastName)
        if (user != null) {
            val indexOfSpace = user.displayName?.indexOf(' ')
            val first = indexOfSpace?.let { user.displayName?.substring(0, it) }
            val last = indexOfSpace?.let { user.displayName?.substring(it + 1) }
            if (indexOfSpace != null) {
                if (indexOfSpace > 0) {
                    editFirstName.setText(first.toString())
                    editLastName.setText(last.toString())
                } else {
                    editFirstName.setText(user.displayName.toString())
                    editLastName.setText("")
                }
            }
        }

        val saveName = findViewById<MaterialButton>(R.id.saveName)
        saveName.setOnClickListener {
            updateUserDisplayName()
        }

        //Mobile

        val sendVerificationButton = findViewById<MaterialButton>(R.id.send_verification_button)
        sendVerificationButton.visibility = View.VISIBLE
        sendVerificationButton.isEnabled = false

        editYourPhone.append(user?.phoneNumber)

        val editPhone = findViewById<TextInputEditText>(R.id.editPhone)
        if (user != null) {
            if (editYourPhone.toString().isNotEmpty()) {
                editPhone?.append(editYourPhone)
            }else{
                editPhone?.append(user.phoneNumber)
            }
        }

        editPhone.addTextChangedListener {
            if (editYourPhone.toString()!=it.toString()) {
                sendVerificationButton.visibility = View.VISIBLE
                sendVerificationButton.isEnabled = true
                sendVerificationButton.text = "Send code"
            }else{
                sendVerificationButton.visibility = View.GONE
                sendVerificationButton.isEnabled = false
            }
        }

        //send code

        sendVerificationButton.setOnClickListener {
            sendVerificationCode()
            sendVerificationButton.visibility = View.VISIBLE
            sendVerificationButton.isEnabled = false
            sendVerificationButton.text = "Sent"
        }

        //verify code

        val verifyButton = findViewById<MaterialButton>(R.id.verify_button)
        verifyButton.setOnClickListener {
            val verificationCode = findViewById<TextInputEditText>(R.id.verification_code_input).text.toString()
            verifyPhoneNumber(verificationId, verificationCode)
        }

        //email
        val verifyEmail = findViewById<MaterialButton>(R.id.verifyEmail)
        val editEmailText = findViewById<TextInputEditText>(R.id.editemailText)

        if (user != null) {
            Log.d("isxoasxdaop","$verNewMail")
            if(verNewMail.isEmpty()) {
                if (editEmailText.text.toString().trim().isEmpty()) {
                    editEmailText.setText(user.email)
                    verifyEmail.isEnabled = false
                } else if (editEmailText.text.toString().trim() == user.email.toString().trim() && user.isEmailVerified) {
                    verifyEmail.text = "Verified"
                    verifyEmail.isEnabled = false
                } else if (user.isEmailVerified) {
                    verifyEmail.text = "Verified"
                    verifyEmail.isEnabled = false
                } else if(editEmailText.text.toString().trim() == user.email.toString().trim()) {
                    verifyEmail.text = "Verify"
                    verifyEmail.isEnabled = true
                    verifyEmail.setOnClickListener {
                        Toast.makeText(this@AccountActivity, "No change found", Toast.LENGTH_SHORT).show()
                    }
                } else{
                    verifyEmail.text = "Verify"
                    verifyEmail.isEnabled = true
                    verifyEmail.setOnClickListener {
                        updateCurrentUserEmail()
                    }
                }
            }else {
                verifyEmail.text = "Try Later"
                verifyEmail.isEnabled = false
            }
        } else{
            Toast.makeText(this@AccountActivity, "Please login", Toast.LENGTH_SHORT).show()
        }

        editEmailText.addTextChangedListener {
            if (user != null) {
                if(verNewMail.isEmpty()) {
                    if (user.isEmailVerified) {
                        verifyEmail.text = "Verified"
                        verifyEmail.isEnabled = false
                    }else if (editEmailText.text.toString().trim() == user.email.toString().trim() && user.isEmailVerified) {
                        verifyEmail.text = "Verified"
                        verifyEmail.isEnabled = false
                    } else if (editEmailText.text.toString().trim() == user.email.toString().trim()) {
                        verifyEmail.text = "Verified"
                        verifyEmail.isEnabled = false
                    } else if (editEmailText.text.toString().trim() == verNewMail.toString().trim() && user.isEmailVerified) {
                        verifyEmail.text = "Verified"
                        verifyEmail.isEnabled = false
                        verifyEmail.setOnClickListener {
                            Toast.makeText(
                                this@AccountActivity,
                                "No change found",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }else {
                        verifyEmail.text = "Verify"
                        verifyEmail.isEnabled = true
                        verifyEmail.setOnClickListener {
                            updateCurrentUserEmail()
                        }
                    }
                }else {
                    verifyEmail.text = "Try Later"
                    verifyEmail.isEnabled = false
                }
            }
        }

        val updatePassword=findViewById<MaterialButton>(R.id.updatePassword)
        updatePassword.setOnClickListener{
            savePassword()
        }
    }

    private fun requestStoragePermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_READ_STORAGE_PERMISSION)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_READ_STORAGE_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, REQUEST_IMAGE_PICKER)
            } else {
                val shouldShowRequestPermissionRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                if (shouldShowRequestPermissionRationale) {
                    // Explain why permission is needed (already done in toast message)
                    Snackbar.make(findViewById(android.R.id.content), "Storage permission is required to access your profile picture.", Snackbar.LENGTH_LONG)
                        .setAction("Grant Permission") { requestStoragePermission() }
                        .show()
                } else {
                    // User might have denied permanently, guide them to app settings
                    Toast.makeText(this, "Service not available", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_PICKER && resultCode == RESULT_OK) {
            val selectedImageUri = data?.data ?: return

            uploadImageToFirebase(selectedImageUri)
        }
    }

    private fun uploadImageToFirebase(imageUri: Uri) {
        val storageReference = FirebaseStorage.getInstance().reference
            .child("profile_images")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)

        storageReference.putFile(imageUri)
            .addOnSuccessListener { snapshot ->
                // Upload successful
                val downloadUrl = snapshot.metadata?.reference?.downloadUrl.toString()
                if (downloadUrl != null) {
                    updateUserProfileImageUrl(downloadUrl)
                }
            }
            .addOnFailureListener { exception ->
                // Handle upload failure
                Toast.makeText(this, "Image upload failed: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateUserProfileImageUrl(imageUrl: String) {
        val user_profile=findViewById<ImageView>(R.id.user_profile)
        Glide.with(this).load(imageUrl).into(user_profile)
    }

    private fun savePassword() {
        val newPassword = findViewById<TextInputEditText>(R.id.editPasswordText).text.toString().trim()
        val confirmPassword = findViewById<TextInputEditText>(R.id.editConfirmPasswordText).text.toString().trim()

        if (newPassword.isBlank() || confirmPassword.isBlank()) {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show()
            return
        } else if (newPassword.isBlank()) {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show()
            return
        } else if (confirmPassword.isBlank()) {
            Toast.makeText(this, "Please Confirm Password", Toast.LENGTH_SHORT).show()
            return
        } else if (newPassword != confirmPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        val firebaseAuth = FirebaseAuth.getInstance()
        val user = firebaseAuth.currentUser

        if (user != null) {
            user.updatePassword(newPassword)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Password has been updated", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Password update failed!", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun updateCurrentUserEmail() {
        val user = firebaseAuth.currentUser
        val editEmailText = findViewById<TextInputEditText>(R.id.editemailText)
        val verifyEmail = findViewById<MaterialButton>(R.id.verifyEmail)

        user?.verifyBeforeUpdateEmail(editEmailText.text.toString().trim())
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    verNewMail.append(editEmailText.text.toString().trim())
                    Toast.makeText(this, "Verification email sent", Toast.LENGTH_LONG).show()
                    verifyEmail.text = "Email Sent"
                    verifyEmail.isEnabled = false
                } else {
                    Toast.makeText(this, "Please login again..", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun updateUserDisplayName() {
        val firstName = findViewById<TextInputEditText>(R.id.editFirstName)
        val newFirstName = firstName.text.toString().trim()

        val lastName = findViewById<TextInputEditText>(R.id.editLastName)
        val newLastName = lastName.text.toString().trim()

        val user = firebaseAuth.currentUser

        if (user != null) {
            if (newFirstName.isNotBlank()) {
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName("$newFirstName $newLastName")
                    .build()

                // Check if the new display name is different from the current one
                if (profileUpdates.displayName != user.displayName) {
                    user.updateProfile(profileUpdates)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "Profile saved", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this, "Profile update failed!", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                } else {
                    Toast.makeText(this, "No change found", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT)
                    .show()
            }
        }else{
            Toast.makeText(this, "Please login again..", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun sendVerificationCode() {
        val country_code=findViewById<CountryCodePicker>(R.id.country_code_picker).selectedCountryCode
        val editPhone=findViewById<TextInputEditText>(R.id.editPhone).text.toString()
        val fullPhoneNumber= "+$country_code$editPhone"

        val auth=FirebaseAuth.getInstance()

        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onCodeSent(verificationId: String, forceResendToken: PhoneAuthProvider.ForceResendingToken) {
                this@AccountActivity.verificationId = verificationId

                // Show UI for entering verification code
                findViewById<TextInputEditText>(R.id.verification_code_input).visibility = View.VISIBLE
                findViewById<MaterialButton>(R.id.verify_button).visibility = View.VISIBLE
                findViewById<MaterialButton>(R.id.send_verification_button).visibility = View.GONE
                findViewById<MaterialButton>(R.id.resend_verification_text).visibility = View.VISIBLE
            }

            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                val sendCode=findViewById<MaterialButton>(R.id.send_verification_button)
                sendCode.text="Verified"
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Toast.makeText(this@AccountActivity, "Verification failed", Toast.LENGTH_SHORT).show()
            }
        }

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(fullPhoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun verifyPhoneNumber(verificationId: String?, verificationCode: String) {
        if (verificationId != null) {
            val credential = PhoneAuthProvider.getCredential(verificationId, verificationCode)
            updateCurrentUserPhoneNumber(credential)
        } else {
            Toast.makeText(this@AccountActivity,"verification failed!",Toast.LENGTH_SHORT).show()        }
    }

    private fun updateCurrentUserPhoneNumber(credential: PhoneAuthCredential) {
        val currentUser = firebaseAuth.currentUser as FirebaseUser

        currentUser.updatePhoneNumber(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this,"Phone Number Updated",Toast.LENGTH_SHORT).show()
                    Log.d("akspieqwddq", "Phone number update success")
                } else {
                    Toast.makeText(this,"update failed!",Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        verNewMail.append("")
        editYourPhone.append("")
    }
}