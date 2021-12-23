package com.alexeyyuditsky.test1.dialogFragments

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.alexeyyuditsky.test1.R

class MultipleSingleChoiceWithConfirmationDialogFragment : DialogFragment() {

    private val color: Int
        get() = requireArguments().getInt(ARG_COLOR)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val colorItems = resources.getStringArray(R.array.colors)
        val colorComponents = mutableListOf(
            Color.red(this.color),
            Color.green(this.color),
            Color.blue(this.color),
        )
        val checkBoxes = colorComponents
            .map { it > 0 && savedInstanceState == null }
            .toBooleanArray()

        val listener = DialogInterface.OnMultiChoiceClickListener { dialog, _, _ ->
            val checkedPositions = (dialog as AlertDialog).listView.checkedItemPositions
            val color = Color.rgb(
                if (checkedPositions[0]) 255 else 0,
                if (checkedPositions[1]) 255 else 0,
                if (checkedPositions[2]) 255 else 0
            )
            parentFragmentManager.setFragmentResult(
                REQUEST_KEY, bundleOf(KEY_COLOR_RESPONSE to color)
            )
        }

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.volume_setup)
            .setMultiChoiceItems(colorItems, checkBoxes, listener)
            .setPositiveButton(R.string.action_close, null)
            .create()
    }

    companion object {
        private val TAG = SingleChoiceDialogFragment::class.java.simpleName
        private val REQUEST_KEY = "$TAG:defaultRequestKey"
        private const val ARG_COLOR = "ARG_COLOR"
        private const val KEY_COLOR_RESPONSE = "KEY_COLOR_RESPONSE"

        fun show(manager: FragmentManager, color: Int) {
            val dialogFragment = MultipleSingleChoiceWithConfirmationDialogFragment()
            dialogFragment.arguments = bundleOf(ARG_COLOR to color)
            dialogFragment.show(manager, TAG)
        }

        fun setupListener(
            manager: FragmentManager,
            lifecycleOwner: LifecycleOwner,
            listener: (Int) -> Unit
        ) {
            manager.setFragmentResultListener(REQUEST_KEY, lifecycleOwner, { _, result ->
                listener(result.getInt(KEY_COLOR_RESPONSE))
            })
        }
    }

}