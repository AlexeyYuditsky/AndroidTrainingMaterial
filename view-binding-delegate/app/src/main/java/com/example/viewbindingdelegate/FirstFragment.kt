package com.example.viewbindingdelegate

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.viewbindingdelegate.databinding.FragmentFirstBinding

class FirstFragment : Fragment(R.layout.fragment_first) {

    private val binding by viewBinding<FragmentFirstBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        log("onViewCreated (FIRST)")
        binding.button.setOnClickListener { launchSecondFragment() }
    }

    private fun launchSecondFragment() {
        parentFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragmentContainer, SecondFragment())
            .commit()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        log("onAttach (FIRST)")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log("onCreate (FIRST)")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        log("onCreateView (FIRST)")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        log("onStart (FIRST)")
    }

    override fun onResume() {
        super.onResume()
        log("onResume (FIRST)")
    }

    override fun onPause() {
        super.onPause()
        log("onPause (FIRST)")
    }

    override fun onStop() {
        super.onStop()
        log("onStop (FIRST)")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        log("onDestroyView (FIRST)")
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy (FIRST)")
    }

    override fun onDetach() {
        super.onDetach()
        log("onDetach (FIRST)")
    }

}