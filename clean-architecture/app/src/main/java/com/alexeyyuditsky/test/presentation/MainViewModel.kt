package com.alexeyyuditsky.test.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexeyyuditsky.domain.model.UserNameParam
import com.alexeyyuditsky.domain.usecase.GetUserNameUseCase
import com.alexeyyuditsky.domain.usecase.SaveUserNameUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val getUserNameUseCase: GetUserNameUseCase,
    private val saveUserNameUseCase: SaveUserNameUseCase
) : ViewModel() {

    private val _nameFlow = MutableSharedFlow<String>(replay = 1)
    val nameFlow = _nameFlow.asSharedFlow()

    fun save(name: String) = viewModelScope.launch {
        val userNameParam = UserNameParam(name = name)
        val result = saveUserNameUseCase.execute(userNameParam)
        _nameFlow.emit("result operation: $result")
    }

    fun load() = viewModelScope.launch {
        val userName = getUserNameUseCase.execute()
        _nameFlow.emit("${userName.firstName} ${userName.lastName}")
    }

}