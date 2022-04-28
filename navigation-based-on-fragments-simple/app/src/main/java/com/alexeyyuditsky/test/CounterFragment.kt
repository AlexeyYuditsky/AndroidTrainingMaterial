package com.alexeyyuditsky.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.alexeyyuditsky.test.databinding.FragmentCounterBinding

class CounterFragment : Fragment() {
    private lateinit var binding: FragmentCounterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCounterBinding.inflate(inflater, container, false)

        binding.counterTextView.text = getString(R.string.screen_label, getCounterValue())
        binding.quoteTextView.text = getQuote()

        binding.launchNextButton.setOnClickListener { launchNext() }
        binding.goBackButton.setOnClickListener { goBack() }

        return binding.root
    }

    private fun launchNext(): Unit = (requireActivity() as MainActivity).launchNext()
    private fun goBack(): Unit = requireActivity().onBackPressed()

    private fun getCounterValue(): Int = requireArguments().getInt(ARG_COUNTER_VALUE)
    private fun getQuote(): String = requireArguments().getString(ARG_QUOTE)!!

    companion object {
        private const val ARG_COUNTER_VALUE = "ARG_COUNTER_VALUE"
        private const val ARG_QUOTE = "ARG_QUOTE"

        fun newInstance(counterValue: Int, quote: String): Fragment {
            return CounterFragment().apply {
                arguments = bundleOf(ARG_COUNTER_VALUE to counterValue, ARG_QUOTE to quote)
            }
        }
    }

}