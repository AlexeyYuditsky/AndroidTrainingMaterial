package com.alexeyyuditsky.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alexeyyuditsky.test.databinding.ActivityCrashBinding

class CrashActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCrashBinding
    private var thread: Thread? = null
    private var timerValue = START_VALUE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrashBinding.inflate(layoutInflater).apply { setContentView(root) }

        binding.startButton.setOnClickListener { startTimer() }

        updateUi()
    }

    private fun startTimer() {
        thread = Thread {
            for (i in START_VALUE downTo 0) {
                timerValue = i
                runOnUiThread { updateUi() }
                Thread.sleep(1000)
            }
            runOnUiThread { stopTimer() }
        }
        thread?.start()
    }

    private fun stopTimer() {
        thread = null
        timerValue = START_VALUE
        updateUi()
    }

    private fun updateUi() {
        val text = resources.getQuantityString(R.plurals.seconds, timerValue, timerValue)
        binding.secondsTextView.text = text
        binding.progressBar.progress = timerValue
        binding.startButton.isEnabled = thread == null
    }

    override fun onDestroy() {
        stopTimer()
        super.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        private const val START_VALUE = 10
    }
}