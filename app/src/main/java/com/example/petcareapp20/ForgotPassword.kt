package com.example.petcareapp20

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class ForgotPassword : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forgot_password)

        firebaseAuth= FirebaseAuth.getInstance()

        val backResettoLogin=findViewById<ImageView>(R.id.backResettoLogin)
        backResettoLogin.setOnClickListener{
            val intent=Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        val resetPassword=findViewById<Button>(R.id.resetPasswordButton)
        resetPassword.setOnClickListener{
            forgotpass()
        }
    }

    private fun forgotpass() {

        val email = findViewById<EditText>(R.id.editEmailReset).text.toString()

        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter your email.", Toast.LENGTH_SHORT).show()
            return
        }

        firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Email sent successfully.", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {

                    val exception = task.exception

                    if (exception is FirebaseAuthInvalidUserException) {
                        showErrorMessage("Invalid email address entered.")
                    } else {
                        showErrorMessage("An unknown error occurred. Please try again.")
                    }
                }
            }
    }

    private fun showErrorMessage(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }
}