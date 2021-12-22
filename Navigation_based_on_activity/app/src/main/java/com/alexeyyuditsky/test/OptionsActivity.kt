package com.alexeyyuditsky.test

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.alexeyyuditsky.test.databinding.ActivityOptionsBinding
import com.alexeyyuditsky.test.model.Options

class OptionsActivity : BaseActivity() {
    private lateinit var binding: ActivityOptionsBinding
    private lateinit var options: Options

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOptionsBinding.inflate(layoutInflater).also { setContentView(it.root) }

        binding.cancelButton.setOnClickListener { onCancelPressed() }
        binding.confirmButton.setOnClickListener { onConfirmPressed() }

        options = savedInstanceState?.getParcelable(KEY_OPTIONS)
            ?: intent.getParcelableExtra(EXTRA_OPTIONS)
                    ?: throw IllegalArgumentException("You need specify EXTRA_OPTIONS argument to launch this activity")

        updateUI()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_OPTIONS, options)
    }

    private fun onConfirmPressed() {
        setResult(RESULT_OK, Intent().putExtra(EXTRA_OPTIONS, options))
        finish()
    }

    private fun onCancelPressed() {
        finish()
    }

    private fun updateUI() {
        binding.apply {
            boxSpinner.setSelection(options.boxCount - 1)
            timerCheckBox.isChecked = options.isTimerEnabled

            boxSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(parent: AdapterView<*>?, v: View?, pos: Int, id: Long) {
                    options = options.copy(boxCount = pos + 1)
                }
            }

            timerCheckBox.setOnClickListener {
                options = options.copy(isTimerEnabled = binding.timerCheckBox.isChecked)
            }
        }
    }

    companion object {
        const val EXTRA_OPTIONS = "options"
        const val KEY_OPTIONS = "options"
    }
}