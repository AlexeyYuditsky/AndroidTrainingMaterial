package com.alexeyyuditsky.test.hello

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alexeyyuditsky.test.base.BaseFragment
import com.alexeyyuditsky.test.base.BaseScreen
import com.alexeyyuditsky.test.base.screenViewModel
import com.alexeyyuditsky.test.databinding.FragmentHelloBinding
import kotlinx.parcelize.Parcelize

class HelloFragment : BaseFragment() { // BaseFragment() обязывает реализовать абстрактный viewModel

    @Parcelize
    class Screen : BaseScreen // класс с помощью которого будем запускать фрагмент HelloFragment

    // делегируем реализацию viewModel через фабрику ViewModelFactory()
    override val viewModel by screenViewModel<HelloViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHelloBinding.inflate(inflater, container, false)

        // прослушиваем изменения пришедшие с экрана EditFragment
        viewModel.currentMessageLiveData.observe(viewLifecycleOwner) {
            binding.valueTextView.text = it
        }

        // делегируем реализацию editButton на viewModel
        binding.editButton.setOnClickListener { viewModel.onEditPressed() }

        return binding.root
    }
}