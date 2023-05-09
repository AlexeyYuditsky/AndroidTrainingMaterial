package com.alexeyyuditsky.test1.dialogFragments

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.LifecycleOwner
import com.alexeyyuditsky.test1.R
import com.alexeyyuditsky.test1.databinding.PartVolumeInputBinding

typealias CustomInputDialogListener = (requestKey: String, volume: Int) -> Unit

class CustomInputDialogFragment : DialogFragment() {

    private lateinit var dialogBinding: PartVolumeInputBinding

    private val volume: Int
        get() = requireArguments().getInt(ARG_VOLUME)

    private val requestKey: String
        get() = requireArguments().getString(ARG_REQUEST_KEY)!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialogBinding = PartVolumeInputBinding.inflate(layoutInflater)
        dialogBinding.volumeInputEditText.setText(volume.toString())

        val dialog = AlertDialog.Builder(requireContext())
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
                parentFragmentManager.setFragmentResult(
                    requestKey, bundleOf(KEY_VOLUME_RESPONSE to volume)
                )
                dismiss()
            }

        }
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

        return dialog
    }

    companion object {
        private val TAG = CustomInputDialogFragment::class.java.simpleName
        private const val ARG_VOLUME = "ARG_VOLUME"
        private const val ARG_REQUEST_KEY = "ARG_REQUEST_KEY"
        private const val KEY_VOLUME_RESPONSE = "KEY_VOLUME_RESPONSE"

        fun show(manager: FragmentManager, volume: Int, requestKey: String) {
            val dialogFragment = CustomInputDialogFragment()
            dialogFragment.arguments = bundleOf(ARG_VOLUME to volume, ARG_REQUEST_KEY to requestKey)
            dialogFragment.show(manager, TAG)
        }

        fun setupListener(
            manager: FragmentManager,
            lifecycleOwner: LifecycleOwner,
            requestKey: String,
            listener: CustomInputDialogListener,
        ) {
            manager.setFragmentResultListener(requestKey, lifecycleOwner) { key, result ->
                listener(key, result.getInt(KEY_VOLUME_RESPONSE))
            }
        }
    }

}