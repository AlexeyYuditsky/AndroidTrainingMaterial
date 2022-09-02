package com.alexeyyuditsky.test.presenter

import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.repository.AuthRepositoryImpl
import kotlinx.coroutines.*
import java.lang.ref.WeakReference

class LoginPresenterImpl : LoginPresenter {

    private val authRepository = AuthRepositoryImpl()
    private var viewState: WeakReference<LoginView>? = null

    fun attachView(view: LoginView) {
        viewState = WeakReference(view)
    }

    override fun login(email: String, password: String) {
        viewState?.get()?.viewsState(false)

        if (!validateEmail(email)) {
            viewState?.get()?.showError(R.string.error_email_invalid)
            viewState?.get()?.viewsState(true)
            return
        }
        if (!validatePassword(password)) {
            viewState?.get()?.showError(R.string.error_password_invalid)
            viewState?.get()?.viewsState(true)
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            val errorMessage = authRepository.login(email, password)

            withContext(Dispatchers.Main) {
                if (errorMessage.isEmpty()) viewState?.get()?.showSuccess()
                else viewState?.get()?.showError(errorMessage)

                viewState?.get()?.viewsState(true)
            }
        }
    }

    private fun validateEmail(email: String): Boolean {
        return email.contains('@') && email.contains('.')
    }

    private fun validatePassword(password: String): Boolean {
        return password.length > 8
    }

}