package com.alexeyyuditsky.bindingadapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alexeyyuditsky.bindingadapter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val employee = Employee(0, "Alex", "Moscow", 15, listOf("Football", "Baseball"))
        val department = Department(0, "IT")

        binding.employee = employee
        binding.department = department
        binding.handler = MyHandler()
        binding.lifecycleOwner = this
    }

}