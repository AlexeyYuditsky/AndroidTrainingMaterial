package com.alexeyyuditsky.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexeyyuditsky.test.repository.AuthRepository
import com.alexeyyuditsky.test.repository.AuthRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val authRepository: AuthRepository = AuthRepositoryImpl()
) : ViewModel() {

    private val _state = MutableSharedFlow<State>()
    val state = _state.asSharedFlow()

    suspend fun login(email: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        _state.emit(State.Loading)

        if (!validateEmail(email)) {
            _state.emit(State.Error(R.string.error_email_invalid))
            return@launch
        }

        if (!validatePassword(password)) {
            _state.emit(State.Error(R.string.error_password_invalid))
            return@launch
        }

        try {
            val result = authRepository.login(email, password)
            if (result.isEmpty()) {
                _state.emit(State.Loaded(R.string.success))
            } else {
                throw Exception()
            }
        } catch (e: Exception) {
            _state.emit(State.Error(R.string.network_exception))
        }
    }

    private fun validateEmail(email: String): Boolean {
        return email.contains('@') && email.contains('.')
    }

    private fun validatePassword(password: String): Boolean {
        return password.length > 8
    }

}