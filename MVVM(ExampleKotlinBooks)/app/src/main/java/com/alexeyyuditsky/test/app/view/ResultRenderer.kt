package com.alexeyyuditsky.test.app.view

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.databinding.PartResultBinding
import com.alexeyyuditsky.test.foundation.model.ErrorResult
import com.alexeyyuditsky.test.foundation.model.PendingResult
import com.alexeyyuditsky.test.foundation.model.Result
import com.alexeyyuditsky.test.foundation.model.SuccessResult
import com.alexeyyuditsky.test.foundation.views.BaseFragment

fun <T> BaseFragment.renderSimpleResult(
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

}

fun BaseFragment.onTryAgain(root: View, call: () -> Unit) {
    root.findViewById<Button>(R.id.tryAgainButton).setOnClickListener { call() }
}