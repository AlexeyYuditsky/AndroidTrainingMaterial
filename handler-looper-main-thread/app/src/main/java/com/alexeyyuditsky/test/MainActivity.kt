package com.alexeyyuditsky.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alexeyyuditsky.test.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }

        binding.crashANRButton.setOnClickListener { openScreen(CrashActivity::class.java) }
        binding.handler1Button.setOnClickListener { openScreen(Handler1Activity::class.java) }
        binding.handler2Button.setOnClickListener { openScreen(Handler2Activity::class.java) }
    }

    private fun openScreen(clazz: Class<*>) {
        startActivity(Intent(this, clazz))
    }
}