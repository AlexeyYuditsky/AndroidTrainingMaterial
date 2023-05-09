package com.alexeyyuditsky.test.presenter

import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.model.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ref.WeakReference

interface LoginPresenter {

    fun login(email: String, password: String)

    class Base(
        private val authRepository: AuthRepository,
    ) : LoginPresenter {

        private var view: WeakReference<LoginView>? = null

        fun attachView(view: LoginView) {
            this.view = WeakReference(view)
        }

        override fun login(email: String, password: String) {
            view?.get()?.viewsState(false)

            if (!validateEmail(email)) {
                view?.get()?.showError(R.string.error_email_invalid)
                view?.get()?.viewsState(true)
                return
            }
            if (!validatePassword(password)) {
                view?.get()?.showError(R.string.error_password_invalid)
                view?.get()?.viewsState(true)
                return
            }

            CoroutineScope(Dispatchers.IO).launch {
                val result = authRepository.login(email, password)

                withContext(Dispatchers.Main) {
                    if (result.isEmpty())
                        view?.get()?.showSuccess()
                    else
                        view?.get()?.showError(result)

                    view?.get()?.viewsState(true)
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

}