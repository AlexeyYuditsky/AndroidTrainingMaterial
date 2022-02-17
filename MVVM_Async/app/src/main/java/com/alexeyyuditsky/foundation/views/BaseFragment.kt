package com.alexeyyuditsky.foundation.views

import android.view.ViewGroup
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.alexeyyuditsky.foundation.model.ErrorResult
import com.alexeyyuditsky.foundation.model.PendingResult
import com.alexeyyuditsky.foundation.model.Result
import com.alexeyyuditsky.foundation.model.SuccessResult

/** Base class for all fragments */
abstract class BaseFragment : Fragment() {

    /** View-model that manages this fragment*/
    abstract val viewModel: BaseViewModel

    /** Call this method when activity controls (e.g. toolbar) should be re-rendered. */
    fun notifyScreenUpdates() = (requireActivity() as FragmentsHolder).notifyScreenUpdates()

    fun <T> renderResult(
        root: ViewGroup,
        result: Result<T>,
        onPending: () -> Unit,
        onError: (Exception) -> Unit,
        onSuccess: (T) -> Unit,
    ) {
        root.children.forEach { it.isVisible = false }
        when (result) {
            is PendingResult -> onPending()
            is ErrorResult -> onError(result.exception)
            is SuccessResult -> onSuccess(result.data)
        }
    }

}