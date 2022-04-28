package com.alexeyyuditsky.test

import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.alexeyyuditsky.test.databinding.ActivityMainBinding
import com.alexeyyuditsky.test.databinding.PartVolumeBinding
import com.alexeyyuditsky.test.databinding.PartVolumeInputBinding
import com.alexeyyuditsky.test.entities.AvailableVolumeValues
import kotlin.properties.Delegates.notNull

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var volume by notNull<Int>()
    private var volume2 by notNull<Int>()
    private var color by notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }

        binding.showDefaultAlertDialogButton.setOnClickListener { showAlertDialog() }
        binding.showSingleChoiceAlertDialogButton.setOnClickListener { showSingleChoiceAlertDialog() }
        binding.showSingleChoiceWithConfirmAlertDialogButton.setOnClickListener { showSingleChoiceWithConfirmAlertDialog() }
        binding.showMultipleChoiceAlertDialogButton.setOnClickListener { showMultipleChoiceAlertDialog() }
        binding.showMultipleChoiceWithConfirmAlertDialogButton.setOnClickListener { showMultipleChoiceWithConfirmAlertDialog() }
        binding.showCustomViewAlertDialogButton.setOnClickListener { showCustomViewAlertDialog() }
        binding.showSingleChoiceCustomAlertDialogButton.setOnClickListener { showSingleChoiceCustomAlertDialog() }
        binding.showInputAndValidationAlertDialogButton.setOnClickListener { showInputAndValidationAlertDialog() }

        volume = savedInstanceState?.getInt(KEY_VOLUME) ?: 23
        volume2 = savedInstanceState?.getInt(KEY_VOLUME_2) ?: 48
        color = savedInstanceState?.getInt(KEY_COLOR) ?: Color.RED

        updateUI()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_VOLUME, volume)
        outState.putInt(KEY_VOLUME_2, volume2)
        outState.putInt(KEY_COLOR, color)
    }

    private fun showInputAndValidationAlertDialog() {
        val dialogBinding = PartVolumeInputBinding.inflate(layoutInflater)
        dialogBinding.volumeInputEditText.setText(volume2.toString())

        val dialog = AlertDialog.Builder(this)
            .setTitle(R.string.volume_setup)
            .setView(dialogBinding.root)
            .setPositiveButton(R.string.action_confirm, null)
            .create()
        dialog.setOnShowListener {
            dialogBinding.volumeInputEditText.requestFocus()

            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
                val enteredText = dialogBinding.volumeInputEditText.text.toString()
                if (enteredText.isBlank()) {
                    dialogBinding.volumeInputEditText.error = getString(R.string.empty_value)
                    return@setOnClickListener
                }
                val volume = enteredText.toIntOrNull()
                if (volume == null || volume > 100) {
                    dialogBinding.volumeInputEditText.error = getString(R.string.invalid_value)
                    return@setOnClickListener
                }
                this.volume2 = volume
                updateUI()
                dialog.dismiss()
            }
        }

        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

        dialog.show()
    }

    private fun showSingleChoiceCustomAlertDialog() {
        val volumeItems = AvailableVolumeValues.createVolumeValues(volume2)
        val adapter = VolumeAdapter(volumeItems.values)

        var volume = this.volume2

        val listenerChoiceItems = DialogInterface.OnClickListener { _, which ->
            volume = adapter.getItem(which)
        }
        val listenerConfirm = DialogInterface.OnClickListener { _, _ ->
            this.volume2 = volume
            updateUI()
        }

        AlertDialog.Builder(this)
            .setTitle(R.string.volume_setup)
            .setSingleChoiceItems(adapter, volumeItems.currentIndex, listenerChoiceItems)
            .setPositiveButton(R.string.action_confirm, listenerConfirm)
            .show()
            .create()
    }

    private fun showCustomViewAlertDialog() {
        val dialogBinding = PartVolumeBinding.inflate(layoutInflater)
        dialogBinding.root.progress = volume2

        val listener = DialogInterface.OnClickListener { _, _ ->
            volume2 = dialogBinding.root.progress
            updateUI()
        }

        AlertDialog.Builder(this)
            .setTitle(R.string.volume_setup)
            .setMessage(R.string.volume_setup_message)
            .setView(dialogBinding.root)
            .setPositiveButton(R.string.action_confirm, listener)
            .show()
            .create()
    }

    private fun showMultipleChoiceWithConfirmAlertDialog() {
        val colorItems = resources.getStringArray(R.array.colors)
        val colorComponents = mutableListOf(
            Color.red(this.color),
            Color.green(this.color),
            Color.blue(this.color),
        )
        val checkBoxes = colorComponents
            .map { it > 0 }
            .toBooleanArray()

        var color = this.color

        val listener = DialogInterface.OnMultiChoiceClickListener { _, which, isChecked ->
            colorComponents[which] = if (isChecked) 255 else 0
            color = Color.rgb(colorComponents[0], colorComponents[1], colorComponents[2])
            updateUI()
        }

        AlertDialog.Builder(this)
            .setTitle(R.string.volume_setup)
            .setMultiChoiceItems(colorItems, checkBoxes, listener)
            .setPositiveButton(R.string.action_confirm) { _, _ ->
                this.color = color
                updateUI()
            }
            .create()
            .show()
    }

    private fun showMultipleChoiceAlertDialog() {
        val colorItems = resources.getStringArray(R.array.colors)
        val colorComponents = mutableListOf(
            Color.red(this.color),
            Color.green(this.color),
            Color.blue(this.color),
        )
        val checkBoxes = colorComponents
            .map { it > 0 }
            .toBooleanArray()

        val listener = DialogInterface.OnMultiChoiceClickListener { _, which, isChecked ->
            colorComponents[which] = if (isChecked) 255 else 0
            this.color = Color.rgb(colorComponents[0], colorComponents[1], colorComponents[2])
            updateUI()
        }

        AlertDialog.Builder(this)
            .setTitle(R.string.volume_setup)
            .setMultiChoiceItems(colorItems, checkBoxes, listener)
            .setPositiveButton(R.string.action_close, null)
            .create()
            .show()
    }

    private fun showSingleChoiceWithConfirmAlertDialog() {
        val volumeItems = AvailableVolumeValues.createVolumeValues(volume)
        val volumeTextItems = volumeItems.values
            .map { getString(R.string.volume_description, it) }
            .toTypedArray()

        val listener = DialogInterface.OnClickListener { dialog, _ ->
            val index = (dialog as AlertDialog).listView.checkedItemPosition
            volume = volumeItems.values[index]
            updateUI()
        }

        AlertDialog.Builder(this)
            .setTitle(R.string.volume_setup)
            .setSingleChoiceItems(volumeTextItems, volumeItems.currentIndex, null)
            .setPositiveButton(R.string.action_confirm, listener)
            .create()
            .show()
    }

    private fun showSingleChoiceAlertDialog() {
        val volumeItems = AvailableVolumeValues.createVolumeValues(volume)
        val volumeTextItems = volumeItems.values
            .map { getString(R.string.volume_description, it) }
            .toTypedArray()

        val listener = DialogInterface.OnClickListener { dialog, which ->
            volume = volumeItems.values[which]
            updateUI()
            dialog.dismiss()
        }

        AlertDialog.Builder(this)
            .setTitle(R.string.volume_setup)
            .setSingleChoiceItems(volumeTextItems, volumeItems.currentIndex, listener)
            .create()
            .show()
    }

    private fun updateUI() {
        binding.currentVolumeTextView.text = getString(R.string.current_volume, volume)
        binding.currentVolumeTextView2.text = getString(R.string.current_volume, volume2)
        binding.view.setBackgroundColor(color)
    }

    private fun showAlertDialog() {
        val listener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> showToast(R.string.uninstall_confirmed)
                DialogInterface.BUTTON_NEGATIVE -> showToast(R.string.uninstall_rejected)
                DialogInterface.BUTTON_NEUTRAL -> showToast(R.string.uninstall_ignored)
            }
        }
        AlertDialog.Builder(this)
            .setCancelable(false)
            .setIcon(R.mipmap.ic_launcher_round)
            .setTitle(R.string.default_alert_title)
            .setMessage(R.string.default_alert_message)
            .setPositiveButton(R.string.action_yes, listener)
            .setNegativeButton(R.string.action_no, listener)
            .setNeutralButton(R.string.action_ignore, listener)
            // вызывается когда нажимаем back или мимо диалога
            .setOnCancelListener { showToast(R.string.dialog_cancelled) }
            // вызывается когда диалог закрывается
            .setOnDismissListener { Log.d("MyLog", "Dialog dismissed") }
            .create()
            .show()
    }

    private fun showToast(@StringRes messageRes: Int) {
        Toast.makeText(this, messageRes, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val KEY_VOLUME = "KEY_VOLUME"
        private const val KEY_VOLUME_2 = "KEY_VOLUME_2"
        private const val KEY_COLOR = "KEY_COLOR"
    }
}