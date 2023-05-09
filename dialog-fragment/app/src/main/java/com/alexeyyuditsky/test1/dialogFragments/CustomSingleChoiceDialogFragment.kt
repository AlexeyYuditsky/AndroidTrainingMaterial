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
import com.alexeyyuditsky.test1.VolumeAdapter
import com.alexeyyuditsky.test1.entities.AvailableVolumeValues

class CustomSingleChoiceDialogFragment : DialogFragment() {

    private val volume: Int
        get() = requireArguments().getInt(ARG_VOLUME)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val volumeItems = AvailableVolumeValues.createVolumeValues(volume)
        val adapter = VolumeAdapter(volumeItems.values)

        val listener = DialogInterface.OnClickListener { dialog, _ ->
            val index = (dialog as AlertDialog).listView.checkedItemPosition
            val volume = volumeItems.values[index]
            parentFragmentManager.setFragmentResult(
                REQUEST_KEY, bundleOf(KEY_VOLUME_RESPONSE to volume)
            )
        }

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.volume_setup)
            .setSingleChoiceItems(adapter, volumeItems.currentIndex, null)
            .setPositiveButton(R.string.action_confirm, listener)
            .create()
    }

    companion object {
        private val TAG = CustomSingleChoiceDialogFragment::class.java.simpleName
        private val REQUEST_KEY = "$TAG:defaultRequestKey"
        private const val KEY_VOLUME_RESPONSE = "KEY_VOLUME_RESPONSE"
        private const val ARG_VOLUME = "ARG_VOLUME"

        fun show(manager: FragmentManager, volume: Int) {
            val dialogFragment = CustomSingleChoiceDialogFragment()
            dialogFragment.arguments = bundleOf(ARG_VOLUME to volume)
            dialogFragment.show(manager, TAG)
        }

        fun setupListener(
            manager: FragmentManager,
            lifecycleOwner: LifecycleOwner,
            listener: (Int) -> Unit
        ) {
            manager.setFragmentResultListener(REQUEST_KEY, lifecycleOwner, { _, result ->
                listener.invoke(result.getInt(KEY_VOLUME_RESPONSE))
            })
        }
    }
}