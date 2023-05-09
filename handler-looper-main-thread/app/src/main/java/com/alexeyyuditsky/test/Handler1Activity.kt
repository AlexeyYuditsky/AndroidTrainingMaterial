package com.alexeyyuditsky.test

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import com.alexeyyuditsky.test.databinding.ActivityHandlerBinding
import kotlin.random.Random

class Handler1Activity : AppCompatActivity() {
    private lateinit var binding: ActivityHandlerBinding
    private val handler = Handler(Looper.getMainLooper())
    private val token = Any()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHandlerBinding.inflate(layoutInflater).apply { setContentView(root) }
        binding.root.forEach {
            if (it is Button && it.id != R.id.testButton)
                it.setOnClickListener(universalButtonListener)
        }
    }

    private val universalButtonListener = View.OnClickListener {
        Thread {
            when (it.id) {
                R.id.enableDisable1Button -> handler.post { toggleTestButtonState() }
                R.id.randomColor1Button -> handler.post { nextRandomColor() }

                R.id.enableDisable2Button -> handler.postDelayed({ toggleTestButtonState() }, 2000L)
                R.id.randomColor2Button -> handler.postDelayed({ nextRandomColor() }, 2000L)

                R.id.randomColor3Button -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    handler.postDelayed({ nextRandomColor() }, token, 2000L)
                }
                R.id.showToastButton -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    handler.postDelayed({ showToast() }, token, 2000L)
                }
                R.id.cancelButton -> handler.removeCallbacksAndMessages(token)
            }
        }.start()
    }

    private fun toggleTestButtonState() {
        binding.testButton.isEnabled = !binding.testButton.isEnabled
    }

    private fun nextRandomColor() {
        val randomColor = -Random.nextInt(255 * 255 * 255)
        binding.colorView.setBackgroundColor(randomColor)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showToast() {
        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show()
    }
}