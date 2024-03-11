package com.example.petcareapp20.mainhome

import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.petcareapp20.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_home) as NavHostFragment
        navController = navHostFragment.navController

        val nav_view=findViewById<BottomNavigationView>(R.id.nav_view)

        nav_view.setupWithNavController(navController)
    }
}