package com.alexeyyuditsky.test

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.alexeyyuditsky.test.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }

        binding.startBackgroundButton.setOnClickListener {
            startService(Intent(this, BackgroundService::class.java))
            binding.progressBar.visibility = View.VISIBLE
        }

        binding.stopBackgroundButton.setOnClickListener {
            stopService(Intent(this, BackgroundService::class.java))
            binding.progressBar.visibility = View.INVISIBLE
        }

        binding.startForegroundButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(Intent(this, ForegroundService::class.java))
            }
            binding.progressBar.visibility = View.VISIBLE
        }

        binding.stopForegroundButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(Intent(this, ForegroundService::class.java))
            }
            binding.progressBar.visibility = View.INVISIBLE
        }

    }

}