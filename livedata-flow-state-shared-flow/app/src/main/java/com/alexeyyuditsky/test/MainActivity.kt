package com.alexeyyuditsky.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.alexeyyuditsky.test.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }

        subscribeToObservables()

        binding.liveDataButton.setOnClickListener {
            viewModel.triggerLiveData()
        }

        binding.stateFlowButton.setOnClickListener {
            viewModel.triggerStateFlow()
        }

        binding.flowButton.setOnClickListener {
            lifecycleScope.launch {
                viewModel.triggerFlow().collectLatest {
                    binding.flowTextView.text = it
                }
            }
        }

        binding.sharedFlowButton.setOnClickListener {
            viewModel.triggerSharedFlow()
        }
    }

    private fun subscribeToObservables() {
        viewModel.liveData.observe(this) {
            binding.liveDataTextView.text = it
        }
        lifecycleScope.launchWhenStarted {
            viewModel.stateFlow.collectLatest {
                binding.stateFlowTextView.text = it
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.sharedFlow.collectLatest {
                binding.sharedTextView.text = it
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

}