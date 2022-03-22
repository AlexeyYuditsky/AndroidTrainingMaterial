package com.alexeyyuditsky.test.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.contract.CustomAction
import com.alexeyyuditsky.test.contract.HasCustomAction
import com.alexeyyuditsky.test.contract.HasCustomTitle
import com.alexeyyuditsky.test.contract.navigator
import com.alexeyyuditsky.test.databinding.FragmentOptionsBinding
import com.alexeyyuditsky.test.model.Options

class OptionsFragment : Fragment(R.layout.fragment_options), HasCustomTitle, HasCustomAction {

    private lateinit var binding: FragmentOptionsBinding

    private lateinit var options: Options

    private lateinit var boxCountItems: List<BoxCountItem>

    private lateinit var adapter: ArrayAdapter<BoxCountItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        options = savedInstanceState?.getParcelable(KEY_OPTIONS)
            ?: arguments?.getParcelable(ARG_OPTIONS)
                    ?: throw IllegalArgumentException("You need to specify options to launch this fragment")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOptionsBinding.bind(view).apply {
            cancelButton.setOnClickListener { onCancelPressed() }
            confirmButton.setOnClickListener { onConfirmPressed() }
        }
        setupSpinner()
        setupCheckbox()
        updateUI()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_OPTIONS, options)
    }

    override fun getTitleRes(): Int {
        return R.string.options
    }

    override fun getCustomAction(): CustomAction {
        return CustomAction(
            iconRes = R.drawable.ic_done,
            textRes = R.string.done,
            onCustomAction = Runnable { onConfirmPressed() }
        )
    }

    private fun setupSpinner() {
        boxCountItems = (1..6).map {
            BoxCountItem(it, resources.getQuantityString(R.plurals.boxes, it, it))
        }
        adapter = ArrayAdapter(requireContext(), R.layout.item_spinner, boxCountItems)
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1)

        binding.boxCountSpinner.adapter = adapter
        binding.boxCountSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    options = options.copy(boxCount = boxCountItems[position].count)
                }
            }
    }

    private fun setupCheckbox() {
        binding.enableTimerCheckBox.setOnClickListener {
            options = options.copy(isTimerEnabled = binding.enableTimerCheckBox.isChecked)
        }
    }

    private fun updateUI() {
        val index = boxCountItems.indexOfFirst { it.count == options.boxCount }
        binding.boxCountSpinner.setSelection(index)
        binding.enableTimerCheckBox.isChecked = options.isTimerEnabled
    }

    private fun onCancelPressed(): Unit = navigator().goBack()

    private fun onConfirmPressed() {
        navigator().publishResult(options)
        navigator().goBack()
    }

    companion object {
        private const val KEY_OPTIONS = "KEY_OPTIONS"
        private const val ARG_OPTIONS = "ARG_OPTIONS"

        fun createArgs(options: Options): Bundle {
            return bundleOf(ARG_OPTIONS to options)
        }
    }

    private class BoxCountItem(
        val count: Int,
        private val optionsTitle: String
    ) {
        override fun toString(): String = optionsTitle
    }
}


