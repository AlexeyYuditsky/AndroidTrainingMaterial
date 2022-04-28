package com.alexeyyuditsky.simplemvvm.view

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.children
import androidx.core.view.isVisible
import com.alexeyyuditsky.foundation.model.ErrorResult
import com.alexeyyuditsky.foundation.model.PendingResult
import com.alexeyyuditsky.foundation.views.BaseFragment
import com.alexeyyuditsky.foundation.model.Result
import com.alexeyyuditsky.foundation.model.SuccessResult
import com.alexeyyuditsky.simplemvvm.R
import com.alexeyyuditsky.simplemvvm.databinding.PartResultBinding


fun <T> BaseFragment.renderSimpleResult(
    root: ViewGroup,
    result: Result<T>,
    onSuccess: (T) -> Unit
) {
    val binding = PartResultBinding.bind(root)
    renderResult(
        root = root,
        result = result,
        onPending = { binding.progressBar.isVisible = true },
        onError = { binding.errorContainer.isVisible = true },
        onSuccess = { successData ->
            root.children
                .filter { it.id != R.id.errorContainer && it.id != R.id.progressBar }
                .forEach { it.isVisible = true }
            onSuccess(successData)
        }
    )
}

fun BaseFragment.onTryAgain(root: View, onTryAgainPressed: () -> Unit) {
    root.findViewById<Button>(R.id.tryAgainButton).setOnClickListener { onTryAgainPressed() }
}

/*fun <T> BaseFragment.renderSimpleResult(
    root: ViewGroup,
    result: Result<T>,
    onSuccess: (T) -> Unit
) {
    val binding = PartResultBinding.bind(root)
    root.children.forEach { it.isVisible = false }
    when (result) {
        is PendingResult -> {
            binding.progressBar.isVisible = true
        }
        is ErrorResult -> {
            binding.errorContainer.isVisible = true
        }
        is SuccessResult -> {
            root.children
                .filter { it.id != R.id.errorContainer && it.id != R.id.progressBar }
                .forEach { it.isVisible = true }
            onSuccess(result.data)
        }
    }
}*/
