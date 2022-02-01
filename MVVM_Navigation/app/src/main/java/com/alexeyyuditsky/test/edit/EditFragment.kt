package com.alexeyyuditsky.test.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.base.BaseFragment
import com.alexeyyuditsky.test.base.BaseScreen
import com.alexeyyuditsky.test.base.screenViewModel
import com.alexeyyuditsky.test.databinding.FragmentEditBinding
import kotlinx.parcelize.Parcelize

class EditFragment : BaseFragment() {

    // класс с помощью которого будем запускать фрагмент EditFragment
    // класс так же используется для передачи аргумента внутри себя во фрагмент EditFragment при запуске
    @Parcelize
    class Screen(val initialValue: String) : BaseScreen

    // создаём viewModel передавая в неё Navigator и EditFragment.Screen
    override val viewModel by screenViewModel<EditViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentEditBinding.inflate(inflater, container, false)

        viewModel.initialMessageEvent.observe(viewLifecycleOwner) { // прослушиваем изменения пришедшие из HelloFragment
            it.getValue()?.let { message -> binding.valueEditText.setText(message) }
        }

        binding.saveButton.setOnClickListener { // делегируем реализацию saveButton на viewModel
            viewModel.onSavePressed(binding.valueEditText.text.toString())
        }

        binding.cancelButton.setOnClickListener { viewModel.onCancelPressed() } // делегируем реализацию cancelButton на viewModel

        return binding.root
    }

}