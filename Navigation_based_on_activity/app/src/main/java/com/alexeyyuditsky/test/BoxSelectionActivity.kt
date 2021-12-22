package com.alexeyyuditsky.test

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.alexeyyuditsky.test.databinding.ActivityBoxSelectionBinding
import com.alexeyyuditsky.test.databinding.ItemBoxBinding
import com.alexeyyuditsky.test.model.Options
import com.google.android.material.snackbar.Snackbar
import java.lang.Long.max
import kotlin.properties.Delegates
import kotlin.random.Random

class BoxSelectionActivity : BaseActivity() {
    private lateinit var binding: ActivityBoxSelectionBinding
    private lateinit var options: Options
    private lateinit var timer: CountDownTimer
    private var boxIndex by Delegates.notNull<Int>()
    private var timerStartTimestamp by Delegates.notNull<Long>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoxSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        options = intent.getParcelableExtra(EXTRA_OPTIONS)
            ?: throw IllegalArgumentException("Cannot launch BoxSelectActivity without options")

        boxIndex = savedInstanceState?.getInt(KEY_INDEX) ?: Random.nextInt(options.boxCount)

        if (options.isTimerEnabled) {
            timerStartTimestamp = savedInstanceState?.getLong(KEY_START_TIMESTAMP)
                ?: System.currentTimeMillis()
            setupTimer()
        }

        createBoxes()
    }

    private fun setupTimer() {
        timer = object : CountDownTimer(TIMER_DURATION, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                updateTimerUI()
            }

            override fun onFinish() {
                showTimerEndDialog()
                updateTimerUI()
            }
        }
    }

    private fun showTimerEndDialog() {
        AlertDialog.Builder(this)
            .setTitle("The end")
            .setMessage("Oops, there is is no enough time, try again later")
            .setCancelable(false)
            .setPositiveButton("Ok") { _, _ -> finish() }
            .create()
            .show()
    }

    private fun getRemainingSeconds(): Long {
        val finishedAt = timerStartTimestamp + TIMER_DURATION
        return (finishedAt - System.currentTimeMillis()) / 1000
    }

    @SuppressLint("SetTextI18n")
    private fun updateTimerUI() {
        if (getRemainingSeconds() > 0)
            binding.timerTextView.text = "Timer: ${getRemainingSeconds()} sec."
        else binding.timerTextView.visibility = View.GONE
    }

    private fun createBoxes() {
        val boxBindings = (0 until options.boxCount).map { index ->
            ItemBoxBinding.inflate(layoutInflater).apply {
                root.id = View.generateViewId()
                root.tag = index
                root.setOnClickListener { view -> onBoxSelected(view) }
                textView.text = getString(R.string.boxTitleTextView, index + 1)
                binding.root.addView(root)
            }
        }
        binding.flow.referencedIds = boxBindings.map { it.root.id }.toIntArray()
    }

    private fun onBoxSelected(view: View) {
        if (view.tag as Int == boxIndex) startActivity(Intent(this, BoxActivity::class.java))
        else Snackbar.make(binding.root, "This box is empty", Snackbar.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        if (options.isTimerEnabled) timer.start()
    }

    override fun onStop() {
        super.onStop()
        if (options.isTimerEnabled) timer.cancel()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_INDEX, boxIndex)
        if (options.isTimerEnabled) outState.putLong(KEY_START_TIMESTAMP, timerStartTimestamp)
    }

    companion object {
        const val EXTRA_OPTIONS = "EXTRA_OPTIONS"
        const val KEY_INDEX = "KEY_INDEX"
        const val KEY_START_TIMESTAMP = "KEY_START_TIMESTAMP"
        const val TIMER_DURATION = 5_000L
    }
}