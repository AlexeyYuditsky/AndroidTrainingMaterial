package com.alexeyyuditsky.test1

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.alexeyyuditsky.test1.databinding.ActivityMainBinding
import com.alexeyyuditsky.test1.dialogFragments.*
import kotlin.properties.Delegates
import kotlin.properties.Delegates.notNull

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var volume by notNull<Int>()
    private var volume2 by notNull<Int>()
    private var volume3 by notNull<Int>()
    private var color by notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }

        binding.showDefaultAlertDialogButton.setOnClickListener { showSimpleDialogFragment() }
        binding.showSingleChoiceAlertDialogButton.setOnClickListener { showSingleChoiceAlertDialog() }
        binding.showSingleChoiceWithConfirmAlertDialogButton.setOnClickListener { showSingleChoiceWithConfirmAlertDialog() }
        binding.showMultipleChoiceAlertDialogButton.setOnClickListener { showMultipleChoiceAlertDialog() }
        binding.showMultipleChoiceWithConfirmAlertDialogButton.setOnClickListener { showMultipleChoiceWithConfirmAlertDialog() }

        binding.showCustomViewAlertDialogButton.setOnClickListener { showCustomViewAlertDialog() }
        binding.showSingleChoiceCustomAlertDialogButton.setOnClickListener { showSingleChoiceCustomAlertDialog() }

        binding.showInputAndValidationAlertDialogButton.setOnClickListener {
            showInputAndValidationAlertDialog(KEY_FIRST_REQUEST_KEY, volume2)
        }
        binding.showInputAndValidationAlertDialogButton2.setOnClickListener {
            showInputAndValidationAlertDialog(KEY_SECOND_REQUEST_KEY, volume3)
        }

        volume = savedInstanceState?.getInt(KEY_VOLUME) ?: 23
        volume2 = savedInstanceState?.getInt(KEY_VOLUME_2) ?: 48
        volume3 = savedInstanceState?.getInt(KEY_VOLUME_3) ?: 89
        color = savedInstanceState?.getInt(KEY_COLOR) ?: Color.RED

        setupSimpleDialogFragmentListener()
        setupSingleChoiceAlertDialog()
        setupSingleChoiceWithConfirmAlertDialog()
        setupMultipleChoiceAlertDialog()
        setupMultipleChoiceWithConfirmAlertDialog()

        setupCustomViewAlertDialog()
        setupSingleChoiceCustomAlertDialog()
        setupInputAndValidationAlertDialog()

        updateUI()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_VOLUME, volume)
        outState.putInt(KEY_VOLUME_2, volume2)
        outState.putInt(KEY_VOLUME_3, volume3)
        outState.putInt(KEY_COLOR, color)
    }

    //-----

    private fun showInputAndValidationAlertDialog(requestKey: String, volume: Int) {
        CustomInputDialogFragment.show(supportFragmentManager, volume, requestKey)
    }

    private fun setupInputAndValidationAlertDialog() {
        val listener: CustomInputDialogListener = { key, result ->
            when (key) {
                KEY_FIRST_REQUEST_KEY -> this.volume2 = result
                KEY_SECOND_REQUEST_KEY -> this.volume3 = result
            }
            updateUI()
        }

        CustomInputDialogFragment.setupListener(
            supportFragmentManager,
            this,
            KEY_FIRST_REQUEST_KEY,
            listener
        )

        CustomInputDialogFragment.setupListener(
            supportFragmentManager,
            this,
            KEY_SECOND_REQUEST_KEY,
            listener
        )
    }

    //-----

    private fun showSingleChoiceCustomAlertDialog() {
        CustomSingleChoiceDialogFragment.show(supportFragmentManager, volume2)
    }

    private fun setupSingleChoiceCustomAlertDialog() {
        CustomSingleChoiceDialogFragment.setupListener(supportFragmentManager, this) {
            this.volume2 = it
            updateUI()
        }
    }

    //-----

    private fun showCustomViewAlertDialog() {
        CustomDialogFragment.show(supportFragmentManager, volume2)
    }

    private fun setupCustomViewAlertDialog() {
        CustomDialogFragment.setupListener(supportFragmentManager, this) {
            this.volume2 = it
            updateUI()
        }
    }

    //-----

    private fun showMultipleChoiceWithConfirmAlertDialog() {
        MultipleSingleChoiceWithConfirmationDialogFragment.show(supportFragmentManager, color)
    }

    private fun setupMultipleChoiceWithConfirmAlertDialog() {
        MultipleSingleChoiceWithConfirmationDialogFragment.setupListener(
            supportFragmentManager,
            this
        ) {
            this.color = it
            updateUI()
        }
    }

    //-----

    private fun showMultipleChoiceAlertDialog() {
        MultipleChoiceDialogFragment.show(supportFragmentManager, color)
    }

    private fun setupMultipleChoiceAlertDialog() {
        MultipleChoiceDialogFragment.setupListener(supportFragmentManager, this) {
            this.color = it
            updateUI()
        }
    }

    //-----

    private fun showSingleChoiceWithConfirmAlertDialog() {
        SingleChoiceWithConfirmationDialogFragment.show(supportFragmentManager, volume)
    }

    private fun setupSingleChoiceWithConfirmAlertDialog() {
        SingleChoiceWithConfirmationDialogFragment.setupListener(supportFragmentManager, this) {
            this.volume = it
            updateUI()
        }
    }

    //-----

    private fun showSingleChoiceAlertDialog() {
        SingleChoiceDialogFragment.show(supportFragmentManager, volume)
    }

    private fun setupSingleChoiceAlertDialog() {
        SingleChoiceDialogFragment.setupListener(supportFragmentManager, this) {
            this.volume = it
            updateUI()
        }
    }

    //-----

    private fun showSimpleDialogFragment() {
        SimpleDialogFragment().show(supportFragmentManager, SimpleDialogFragment.TAG)
    }

    private fun setupSimpleDialogFragmentListener() {
        supportFragmentManager.setFragmentResultListener(
            SimpleDialogFragment.REQUEST_KEY, this
        ) { _, result ->
            when (result.getInt(SimpleDialogFragment.KEY_RESPONSE)) {
                DialogInterface.BUTTON_POSITIVE -> showToast(R.string.uninstall_confirmed)
                DialogInterface.BUTTON_NEGATIVE -> showToast(R.string.uninstall_rejected)
                DialogInterface.BUTTON_NEUTRAL -> showToast(R.string.uninstall_ignored)
            }
        }
    }

    //-----

    private fun updateUI() {
        binding.currentVolumeTextView.text = getString(R.string.current_volume, volume)
        binding.currentVolumeTextView2.text = getString(R.string.current_volume, volume2)
        binding.currentVolumeTextView3.text = getString(R.string.current_volume, volume3)
        binding.view.setBackgroundColor(color)
    }

    companion object {
        private const val KEY_VOLUME = "KEY_VOLUME"
        private const val KEY_VOLUME_2 = "KEY_VOLUME_2"
        private const val KEY_VOLUME_3 = "KEY_VOLUME_3"
        private const val KEY_COLOR = "KEY_COLOR"

        private const val KEY_FIRST_REQUEST_KEY = "KEY_VOLUME_FIRST_REQUEST_KEY"
        private const val KEY_SECOND_REQUEST_KEY = "KEY_VOLUME_SECOND_REQUEST_KEY"
    }
}