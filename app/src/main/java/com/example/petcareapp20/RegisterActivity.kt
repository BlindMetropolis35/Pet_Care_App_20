package com.example.petcareapp20

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.petcareapp20.mainhome.HomeActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.auth.User

class RegisterActivity : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        firebaseAuth= FirebaseAuth.getInstance()

        val emailToReg=intent.getStringExtra("emailToReg")
        val email = findViewById<EditText>(R.id.editEmail)
        email.setText(emailToReg)

        val btnSignup=findViewById<MaterialButton>(R.id.btnSignup)
        btnSignup.setOnClickListener{
            signup()
        }

        val btnsigntologin=findViewById<TextView>(R.id.textViewLogin)
        btnsigntologin.setOnClickListener{
            val intent= Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
    private fun signup() {
        val email = findViewById<EditText>(R.id.editEmail).text.toString()
        val password = findViewById<EditText>(R.id.editPassword).text.toString()
        val confirmPassword = findViewById<EditText>(R.id.editConfirmPassword).text.toString()

        if (email.isBlank() && password.isBlank() && confirmPassword.isBlank()) {
            Toast.makeText(this, "Please fill in all fields*", Toast.LENGTH_SHORT).show()
            return
        } else if (email.isBlank()) {
            Toast.makeText(this, "Please enter your email address", Toast.LENGTH_SHORT).show()
            return
        } else if (password.isBlank()) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show()
            return
        } else if (confirmPassword.isBlank()) {
            Toast.makeText(this, "Please confirm your password", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    if (user != null) {
                        sendVerificationEmail(user)
                    }
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Registration failed: ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun sendVerificationEmail(user: FirebaseUser) {
        user.sendEmailVerification()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Registration successful! Verification email sent.", Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(this, "Error sending verification email.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}