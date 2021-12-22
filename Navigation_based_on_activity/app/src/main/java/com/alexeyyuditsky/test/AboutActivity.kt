package com.alexeyyuditsky.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alexeyyuditsky.test.databinding.ActivityAboutBinding

class AboutActivity : BaseActivity() {
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater).also { setContentView(it.root) }

        binding.versionNameTextView.text = BuildConfig.VERSION_NAME
        binding.versionCodeTextView.text = BuildConfig.VERSION_CODE.toString()
        binding.okButton.setOnClickListener { onOkPressed() }
    }

    private fun onOkPressed() { finish() }
}