package com.alexeyyuditsky.test1.dialogFragments

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.alexeyyuditsky.test1.R
import com.alexeyyuditsky.test1.entities.AvailableVolumeValues

class SingleChoiceWithConfirmationDialogFragment : DialogFragment() {

    private val volume: Int
        get() = requireArguments().getInt(ARG_VALUE)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val volumeItems = AvailableVolumeValues.createVolumeValues(volume)
        val volumeTextItems = volumeItems.values
            .map { getString(R.string.volume_description, it) }
            .toTypedArray()

        val listener = DialogInterface.OnClickListener { dialog, _ ->
            val index = (dialog as AlertDialog).listView.checkedItemPosition
            val volume = volumeItems.values[index]
            parentFragmentManager.setFragmentResult(
                REQUEST_KEY,
                bundleOf(KEY_VOLUME_RESPONSE to volume)
            )
        }

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.volume_setup)
            .setSingleChoiceItems(volumeTextItems, volumeItems.currentIndex, null)
            .setPositiveButton(R.string.action_confirm, listener)
            .create()
    }


    companion object {
        private val TAG = SingleChoiceDialogFragment::class.java.simpleName
        private val REQUEST_KEY = "$TAG:defaultRequestKey"
        private const val ARG_VALUE = "ARG_VALUE"
        private const val KEY_VOLUME_RESPONSE = "KEY_VOLUME_RESPONSE"

        fun show(manager: FragmentManager, volume: Int) {
            val dialogFragment = SingleChoiceWithConfirmationDialogFragment()
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