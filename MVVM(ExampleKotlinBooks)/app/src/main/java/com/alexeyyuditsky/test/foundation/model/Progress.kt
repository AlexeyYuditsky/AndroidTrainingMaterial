package com.alexeyyuditsky.test.foundation.model

sealed class Progress

object EmptyProgress : Progress()

class PercentageProgress(
    var percentage: Int
) : Progress() {

    companion object {
        val START = PercentageProgress(percentage = 0)
    }

}

fun Progress.isInProgress() = this !is EmptyProgress

fun Progress.getPercentage() =
    (this as? PercentageProgress)?.percentage ?: PercentageProgress.START.percentage
