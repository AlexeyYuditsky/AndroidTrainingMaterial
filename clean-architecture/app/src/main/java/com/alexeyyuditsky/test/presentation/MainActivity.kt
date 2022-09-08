package com.alexeyyuditsky.test.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alexeyyuditsky.data.repository.UserRepositoryImpl
import com.alexeyyuditsky.data.storage.sharedpref.SharedPrefUserStorage
import com.alexeyyuditsky.test.databinding.ActivityMainBinding
import com.alexeyyuditsky.domain.model.UserNameParam
import com.alexeyyuditsky.domain.usecase.GetUserNameUseCase
import com.alexeyyuditsky.domain.usecase.SaveUserNameUseCase

class MainActivity : AppCompatActivity() {

    private val userStorage by lazy((LazyThreadSafetyMode.NONE)) { SharedPrefUserStorage(applicationContext) }
    private val userRepository by lazy(LazyThreadSafetyMode.NONE) { UserRepositoryImpl(userStorage) }
    private val getUserNameUseCase by lazy(LazyThreadSafetyMode.NONE) { GetUserNameUseCase(userRepository) }
    private val saveUserNameUseCase by lazy(LazyThreadSafetyMode.NONE) { SaveUserNameUseCase(userRepository) }

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.getNameButton.setOnClickListener {
            val userName = getUserNameUseCase.execute()
            binding.textView.text = "$userName"
        }

        binding.saveNameButton.setOnClickListener {
            val name = binding.editText.text.toString()
            val userNameParam = UserNameParam(name = name)
            val result = saveUserNameUseCase.execute(userNameParam)
            binding.textView.text = "Save result = $result"
        }
    }

}


