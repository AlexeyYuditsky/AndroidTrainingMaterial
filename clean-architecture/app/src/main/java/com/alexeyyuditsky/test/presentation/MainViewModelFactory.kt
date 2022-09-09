package com.alexeyyuditsky.test.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexeyyuditsky.data.repository.UserRepositoryImpl
import com.alexeyyuditsky.data.storage.sharedpref.SharedPrefUserStorage
import com.alexeyyuditsky.domain.usecase.GetUserNameUseCase
import com.alexeyyuditsky.domain.usecase.SaveUserNameUseCase

class MainViewModelFactory(context: Context) : ViewModelProvider.Factory {

    private val userStorage = SharedPrefUserStorage(context)
    private val userRepository = UserRepositoryImpl(userStorage)
    private val getUserNameUseCase = GetUserNameUseCase(userRepository)
    private val saveUserNameUseCase = SaveUserNameUseCase(userRepository)

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(
            getUserNameUseCase = getUserNameUseCase,
            saveUserNameUseCase = saveUserNameUseCase
        ) as T
    }

}