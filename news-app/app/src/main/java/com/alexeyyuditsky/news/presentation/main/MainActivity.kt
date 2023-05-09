package com.alexeyyuditsky.news.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.alexeyyuditsky.news.Injection
import com.alexeyyuditsky.news.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        Injection.init(applicationContext)
        super.onCreate(savedInstanceState)
        setupNavController()
    }

    private fun setupNavController() {
        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController = navHost.navController
        NavigationUI.setupWithNavController(findViewById<BottomNavigationView>(R.id.bottomNavView), navController)
    }

}