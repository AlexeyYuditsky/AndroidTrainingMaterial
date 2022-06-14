package com.alexeyyuditsky.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.alexeyyuditsky.test.databinding.ActivityMainBinding
import com.alexeyyuditsky.test.screens.main.ListViewModel
import com.alexeyyuditsky.test.utils.viewModelCreator
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModelCreator { MainViewModel(Repositories.employeesRepository) }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Repositories.init(applicationContext)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }
        setSupportActionBar(binding.toolbar)
        setupEnableErrorCheckBox()
    }

    override fun onBackPressed() {
        if (binding.searchEditText.isFocused) {
            binding.searchEditText.text.clear()
            binding.searchEditText.clearFocus()
        } else super.onBackPressed()
    }

    private fun setupEnableErrorCheckBox() {
        lifecycleScope.launch {
            viewModel.isErrorsEnabled.collectLatest {
                binding.errorCheckBox.isChecked = it
            }
        }
        binding.errorCheckBox.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setEnableError(isChecked)
        }
    }

}
