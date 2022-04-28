package com.alexeyyuditsky.test.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alexeyyuditsky.test.BuildConfig
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.contract.HasCustomTitle
import com.alexeyyuditsky.test.contract.navigator
import com.alexeyyuditsky.test.databinding.FragmentAboutBinding

class AboutFragment : Fragment(), HasCustomTitle {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentAboutBinding.inflate(inflater, container, false).apply {
            versionNameTextView.text = BuildConfig.VERSION_NAME
            versionCodeTextView.text = BuildConfig.VERSION_CODE.toString()
            okButton.setOnClickListener { onOkPressed() }
        }.root
    }

    override fun getTitleRes(): Int = R.string.about

    private fun onOkPressed(): Unit = navigator().goBack()

}