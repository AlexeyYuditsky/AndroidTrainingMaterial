package com.alexeyyuditsky.test.contract

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.alexeyyuditsky.test.model.Options

typealias ResultListener<T> = (T) -> Unit

fun Fragment.navigator(): Navigator = requireActivity() as Navigator

interface Navigator {

    fun showCongratulationsScreen()

    fun showBoxSelectionScreen(options: Options)

    fun showOptionsScreen(options: Options)

    fun showAboutScreen()

    fun goBack()

    fun goToMenu()

    fun <T : Parcelable> publishResult(result: T)

    fun <T : Parcelable> listenResult(
        key: String,
        owner: LifecycleOwner,
        listener: ResultListener<T>
    )

}