package com.alexeyyuditsky.room.screens.tabs.admin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.alexeyyuditsky.room.R
import com.alexeyyuditsky.room.databinding.FragmentAdminBinding

class AdminFragment : Fragment(R.layout.fragment_admin) {

    private lateinit var binding: FragmentAdminBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAdminBinding.bind(view)
    }

}