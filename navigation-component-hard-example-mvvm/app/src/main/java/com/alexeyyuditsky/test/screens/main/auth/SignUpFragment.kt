package com.alexeyyuditsky.test.screens.main.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private lateinit var binding: FragmentSignUpBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpBinding.bind(view)
        binding.createAccountButton.setOnClickListener {  }


    }

}