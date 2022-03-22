package com.alexeyyuditsky.test.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.contract.HasCustomTitle
import com.alexeyyuditsky.test.contract.navigator
import com.alexeyyuditsky.test.databinding.FragmentBoxBinding

class BoxFragment : Fragment(R.layout.fragment_box), HasCustomTitle {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentBoxBinding.bind(view).apply {
            toMainMenuButton.setOnClickListener { onToMainMenuPressed() }
        }
    }

    override fun getTitleRes(): Int {
        return R.string.box
    }

    private fun onToMainMenuPressed() = navigator().goToMenu()

}