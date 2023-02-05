package com.alexeyyuditsky.test

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.alexeyyuditsky.test.databinding.ActivityMainBinding
import com.alexeyyuditsky.test.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val authRepository = AuthRepository.Base()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.click.setOnClickListener { onButtonClick() }
    }

    private fun onButtonClick() = lifecycleScope.launch {
        viewsState(false)

        if (!validateEmail()) {
            Toast.makeText(this@MainActivity, R.string.error_email_invalid, Toast.LENGTH_SHORT).show()
            viewsState(true)
            return@launch
        }
        if (!validatePassword()) {
            Toast.makeText(this@MainActivity, R.string.error_password_invalid, Toast.LENGTH_SHORT).show()
            viewsState(true)
            return@launch
        }

        withContext(Dispatchers.IO) {
            val result = authRepository.login(
                email = binding.login.text.toString(),
                password = binding.password.text.toString()
            )

            withContext(Dispatchers.Main) {
                if (result.isEmpty()) {
                    Toast.makeText(this@MainActivity, "Success login", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewsState(true)
    }

    private fun viewsState(state: Boolean) {
        binding.login.isEnabled = state
        binding.password.isEnabled = state
        binding.click.isEnabled = state
    }

    private fun validateEmail(): Boolean {
        return binding.login.text.toString().contains('@') && binding.login.text.toString().contains('.')
    }

    private fun validatePassword(): Boolean {
        return binding.password.text.toString().length > 8
    }

}