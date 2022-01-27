package com.alexeyyuditsky.test

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts.*
import com.alexeyyuditsky.test.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val requestPermissionLauncher = registerForActivityResult(RequestPermission()) {
        Log.d(MainActivity::class.java.simpleName, "Permission granted: $it")
    }

    private val editMessage = registerForActivityResult(SecondActivity.Contract()) {
        Log.d(MainActivity::class.java.simpleName, "Edit result: $it")
        if (it.confirmed) {
            binding.valueTextView.text = it.message
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }

        binding.requestPermissionButton.setOnClickListener { requestPermission() }

        binding.editButton.setOnClickListener { editMessage() }
    }

    private fun requestPermission() {
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun editMessage() {
        editMessage.launch(binding.valueTextView.text.toString())
    }

}