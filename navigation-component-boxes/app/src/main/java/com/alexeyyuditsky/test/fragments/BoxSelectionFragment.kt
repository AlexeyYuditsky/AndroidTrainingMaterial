package com.alexeyyuditsky.test.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.contract.HasCustomTitle
import com.alexeyyuditsky.test.contract.navigator
import com.alexeyyuditsky.test.databinding.FragmentBoxSelectionBinding
import com.alexeyyuditsky.test.databinding.ItemBoxBinding
import com.alexeyyuditsky.test.model.Options
import com.google.android.material.snackbar.Snackbar
import java.lang.Long.max
import kotlin.properties.Delegates
import kotlin.random.Random

class BoxSelectionFragment : Fragment(R.layout.fragment_box_selection), HasCustomTitle {

    private lateinit var binding: FragmentBoxSelectionBinding

    private lateinit var options: Options

    private var timerHandler: TimerHandler? = null

    private var boxIndex by Delegates.notNull<Int>()

    private var timerStartTimestamp by Delegates.notNull<Long>()

    private var alreadyDone = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        options = arguments?.getParcelable(ARG_OPTIONS)
            ?: throw IllegalArgumentException("You need to specify options to launch this fragment")

        boxIndex = savedInstanceState?.getInt(KEY_INDEX) ?: Random.nextInt(options.boxCount)

        timerHandler = if (options.isTimerEnabled) TimerHandler() else null
        timerHandler?.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBoxSelectionBinding.bind(view)
        createBoxes()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_INDEX, boxIndex)
        timerHandler?.onSaveInstanceState(outState)
    }

    override fun onStart() {
        super.onStart()
        timerHandler?.onStart()
    }

    override fun onStop() {
        super.onStop()
        timerHandler?.onStop()
    }

    override fun getTitleRes(): Int {
        return R.string.select_box
    }

    private fun createBoxes() {
        val boxBindings = (0 until options.boxCount).map { index ->
            val boxBinding = ItemBoxBinding.inflate(layoutInflater)
            boxBinding.root.id = View.generateViewId()
            boxBinding.root.tag = index
            boxBinding.root.setOnClickListener { view -> onBoxSelected(view) }
            boxBinding.boxTitleTextView.text = getString(R.string.box_title, index + 1)
            binding.root.addView(boxBinding.root)
            boxBinding
        }
        binding.flow.referencedIds = boxBindings.map { it.root.id }.toIntArray()
    }

    private fun onBoxSelected(view: View) {
        if (view.tag as Int == boxIndex) {
            alreadyDone = true // disabling timer if the user made right choice
            navigator().showCongratulationsScreen()
        } else Snackbar.make(binding.root, R.string.empty_box, Snackbar.LENGTH_SHORT).show()
    }

    private fun updateTimerUi() {
        if (getRemainingSeconds() >= 0) {
            binding.timerTextView.visibility = View.VISIBLE
            binding.timerTextView.text = getString(R.string.timer_value, getRemainingSeconds())
        } else binding.timerTextView.visibility = View.GONE
    }

    private fun showTimerEndDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.the_end))
            .setMessage(getString(R.string.timer_end_message))
            .setCancelable(false)
            .setPositiveButton(android.R.string.ok) { _, _ -> navigator().goBack() }
            .create()
            .show()
    }

    private fun getRemainingSeconds(): Long {
        val finishedAt = timerStartTimestamp + TIMER_DURATION
        return max(0, (finishedAt - System.currentTimeMillis()) / 1000)
    }

    inner class TimerHandler {
        private lateinit var timer: CountDownTimer

        fun onCreate(savedInstanceState: Bundle?) {
            timerStartTimestamp = savedInstanceState?.getLong(KEY_START_TIMESTAMP)
                ?: System.currentTimeMillis()
            alreadyDone = savedInstanceState?.getBoolean(KEY_ALREADY_DONE) ?: false
        }

        fun onSaveInstanceState(outState: Bundle) {
            outState.putLong(KEY_START_TIMESTAMP, timerStartTimestamp)
            outState.putBoolean(KEY_ALREADY_DONE, alreadyDone)
        }

        fun onStart() {
            if (alreadyDone) return
            timer = object : CountDownTimer(getRemainingSeconds() * 1000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    updateTimerUi()
                }

                override fun onFinish() {
                    updateTimerUi()
                    showTimerEndDialog()
                }
            }
            updateTimerUi()
            timer.start()
        }

        fun onStop() {
            timer.cancel()
        }
    }

    companion object {
        private const val ARG_OPTIONS = "ARG_OPTIONS"
        private const val KEY_INDEX = "KEY_INDEX"
        private const val KEY_START_TIMESTAMP = "KEY_START_TIMESTAMP"
        private const val KEY_ALREADY_DONE = "KEY_ALREADY_DONE"
        private const val TIMER_DURATION = 10_000L

        fun createArgs(options: Options): Bundle {
            return bundleOf(ARG_OPTIONS to options)
        }
    }

}