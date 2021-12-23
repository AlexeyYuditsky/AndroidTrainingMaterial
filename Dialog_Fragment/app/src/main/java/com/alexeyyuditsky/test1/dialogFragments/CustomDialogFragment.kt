package com.alexeyyuditsky.test1.dialogFragments

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.alexeyyuditsky.test1.R
import com.alexeyyuditsky.test1.databinding.PartVolumeBinding

class CustomDialogFragment : DialogFragment() {
    private lateinit var dialogBinding: PartVolumeBinding

    private val volume: Int
        get() = requireArguments().getInt(ARG_VALUE)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialogBinding = PartVolumeBinding.inflate(layoutInflater)
        dialogBinding.root.progress = savedInstanceState?.getInt(KEY_ARG_BUNDLE) ?: volume

        val listener = DialogInterface.OnClickListener { _, _ ->
            val volume = dialogBinding.root.progress
            parentFragmentManager.setFragmentResult(
                REQUEST_KEY, bundleOf(KEY_VOLUME_RESPONSE to volume)
            )
        }

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.volume_setup)
            .setMessage(R.string.volume_setup_message)
            .setView(dialogBinding.root)
            .setPositiveButton(R.string.action_confirm, listener)
            .show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_ARG_BUNDLE, dialogBinding.root.progress)
    }

    companion object {
        private val TAG = CustomDialogFragment::class.java.simpleName
        private val REQUEST_KEY = "$TAG:defaultRequestKey"
        private const val ARG_VALUE = "ARG_VALUE"
        private const val KEY_VOLUME_RESPONSE = "KEY_VOLUME_RESPONSE"
        private const val KEY_ARG_BUNDLE = "KEY_ARG_BUNDLE"

        fun show(manager: FragmentManager, volume: Int) {
            val dialogFragment = CustomDialogFragment()
            dialogFragment.arguments = bundleOf(ARG_VALUE to volume)
            dialogFragment.show(manager, TAG)
        }

        fun setupListener(
            manager: FragmentManager,
            lifecycleOwner: LifecycleOwner,
            listener: (Int) -> Unit
        ) {
            manager.setFragmentResultListener(REQUEST_KEY, lifecycleOwner, { _, result ->
                listener(result.getInt(KEY_VOLUME_RESPONSE))
            })
        }
    }
}