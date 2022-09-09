package com.alexeyyuditsky.test.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.alexeyyuditsky.data.repository.UserRepositoryImpl
import com.alexeyyuditsky.data.storage.sharedpref.SharedPrefUserStorage
import com.alexeyyuditsky.test.databinding.ActivityMainBinding
import com.alexeyyuditsky.domain.model.UserNameParam
import com.alexeyyuditsky.domain.usecase.GetUserNameUseCase
import com.alexeyyuditsky.domain.usecase.SaveUserNameUseCase
import com.alexeyyuditsky.test.log
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this, MainViewModelFactory(applicationContext))[MainViewModel::class.java]
    }

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

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


