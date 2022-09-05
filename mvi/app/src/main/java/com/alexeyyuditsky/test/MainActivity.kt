package com.alexeyyuditsky.test

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.alexeyyuditsky.test.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.click.setOnClickListener { onButtonClick() }

        observeState()
    }

    private fun observeState() = lifecycleScope.launch {
        viewModel.state.collectLatest {
            when (it) {
                is State.Loading -> viewsState(false)
                is State.Loaded -> {
                    viewsState(true)
                    showToast(it.message)
                }
                is State.Error -> {
                    viewsState(true)
                    showToast(it.message)
                }
            }
        }
    }

    private fun showToast(@StringRes res: Int) {
        Toast.makeText(this@MainActivity, res, Toast.LENGTH_SHORT).show()
    }

    private fun viewsState(state: Boolean) {
        binding.login.isEnabled = state
        binding.password.isEnabled = state
        binding.click.isEnabled = state
    }

    private fun onButtonClick() = lifecycleScope.launch {
        viewModel.login(
            email = binding.login.text.toString(),
            password = binding.password.text.toString()
        )
    }

}