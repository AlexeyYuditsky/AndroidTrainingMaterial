package com.alexeyyuditsky.test

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.alexeyyuditsky.test.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }

        binding.bottomButtons.apply {
            setListener {
                when (it) {
                    BottomButtonAction.NEGATIVE -> {
                        bottomNegativeButtonText = "Updated rejected"
                        bottomNegativeBackgroundColor = Color.GREEN
                    }
                    BottomButtonAction.POSITIVE -> {
                        bottomPositiveButtonText = "Updated apply"
                        bottomPositiveBackgroundColor = Color.BLUE
                    }
                }
            }
        }

    }

}