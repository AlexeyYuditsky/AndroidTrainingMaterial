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
        if (!validateEmail(email)) {
            viewState?.get()?.showError(R.string.error_email_invalid)
            return
        }
        if (!validatePassword(password)) {
            viewState?.get()?.showError(R.string.error_password_invalid)
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            val errorMessage = authRepository.login(email, password).await()

            if (errorMessage.isEmpty())
                withContext(Dispatchers.Main) { viewState?.get()?.showSuccess() }
            else
                withContext(Dispatchers.Main) { viewState?.get()?.showError(errorMessage) }
        }
    }

    private fun validateEmail(email: String): Boolean {
        return email.contains('@') && email.contains('.')
    }

    private fun validatePassword(password: String): Boolean {
        return password.length > 8
    }

}