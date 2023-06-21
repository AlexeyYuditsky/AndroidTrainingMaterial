package com.example.viewbindingdelegate

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.viewbindingdelegate.databinding.FragmentSecondBinding

class SecondFragment : Fragment(R.layout.fragment_second) {

    private val binding by viewBinding<FragmentSecondBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        log("onViewCreated (SECOND)")
        binding.button.setOnClickListener { launchFirstFragment() }
    }

    private fun launchFirstFragment() {
        parentFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragmentContainer, FirstFragment())
            .commit()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        log("onAttach (SECOND)")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log("onCreate (SECOND)")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        log("onCreateView (SECOND)")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        log("onStart (SECOND)")
    }

    override fun onResume() {
        super.onResume()
        log("onResume (SECOND)")
    }

    override fun onPause() {
        super.onPause()
        log("onPause (SECOND)")
    }

    override fun onStop() {
        super.onStop()
        log("onStop (SECOND)")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        log("onDestroyView (SECOND)")
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy (SECOND)")
    }

    override fun onDetach() {
        super.onDetach()
        log("onDetach (SECOND)")
    }

}
