package com.alexeyyuditsky.test.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.alexeyyuditsky.test.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainViewModel>()

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        lifecycleScope.launch {
            viewModel.nameFlow.collectLatest {
                binding.textView.text = it
            }
        }

        binding.getNameButton.setOnClickListener {
            viewModel.load()
        }

        binding.saveNameButton.setOnClickListener {
            val name = binding.editText.text.toString()
            viewModel.save(name)
        }
    }

}


