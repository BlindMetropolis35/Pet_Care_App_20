package com.example.petcareapp20

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.petcareapp20.donation.PayDonation
import com.example.petcareapp20.mainhome.HomeActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthCredential

class LoginActivity : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        //firebase auth
        firebaseAuth=FirebaseAuth.getInstance()

        if (isUserLoggedIn()) {
            Log.d("StartIntent","Startingintent")
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags =Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
            Log.d("StartIntent","success")
            return
        }

        val btnLogin=findViewById<MaterialButton>(R.id.btnLogin)
        btnLogin.setOnClickListener{
            login()
        }

        val btnlogintosignup=findViewById<MaterialButton>(R.id.LogtoSignUp)
        btnlogintosignup.setOnClickListener{
            val intent=Intent(this,RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        val forgotPassword=findViewById<TextView>(R.id.forgotPassword)
        forgotPassword.setOnClickListener{
            val intent=Intent(this,ForgotPassword::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun isUserLoggedIn(): Boolean {
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    //firebase auth
    private fun login(){
        val email=findViewById<EditText>(R.id.editEmail).text.toString()
        val password=findViewById<EditText>(R.id.editPassword).text.toString()

        if(email.isBlank()&&password.isBlank()){
            Toast.makeText(this,"Please enter your email and password",Toast.LENGTH_SHORT).show()
            return
        }
        else if(email.isBlank()){
            Toast.makeText(this,"Please enter your email address",Toast.LENGTH_SHORT).show()
            return
        }
        else if(password.isBlank()){
            Toast.makeText(this,"Please enter your password",Toast.LENGTH_SHORT).show()
            return
        }

        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    Toast.makeText(this,"Login Successful", Toast.LENGTH_SHORT).show()
                    saveLoginState(true)
                    val intent=Intent(this,HomeActivity::class.java)
                    intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(this, "Login failed: Invalid Email or password is incorrect", Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun saveLoginState(isLoggedIn: Boolean) {
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", isLoggedIn)
        editor.apply()
    }
}