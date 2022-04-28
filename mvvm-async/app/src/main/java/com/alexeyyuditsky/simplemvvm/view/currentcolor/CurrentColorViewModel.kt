package com.alexeyyuditsky.simplemvvm.view.currentcolor

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.alexeyyuditsky.foundation.model.PendingResult
import com.alexeyyuditsky.foundation.model.SuccessResult
import com.alexeyyuditsky.foundation.model.takeSuccess
import com.alexeyyuditsky.simplemvvm.R
import com.alexeyyuditsky.simplemvvm.model.colors.ColorListener
import com.alexeyyuditsky.simplemvvm.model.colors.ColorsRepository
import com.alexeyyuditsky.simplemvvm.model.colors.NamedColor
import com.alexeyyuditsky.foundation.navigator.Navigator
import com.alexeyyuditsky.foundation.uiactions.UIActions
import com.alexeyyuditsky.foundation.views.BaseViewModel
import com.alexeyyuditsky.foundation.views.LiveResult
import com.alexeyyuditsky.foundation.views.MutableLiveResult
import com.alexeyyuditsky.simplemvvm.view.changecolor.ChangeColorFragment
import kotlinx.coroutines.*
import java.util.concurrent.CancellationException

class CurrentColorViewModel(
    private val navigator: Navigator, // IntermediateNavigator
    private val uiActions: UIActions, // AndroidUIActions
    private val colorsRepository: ColorsRepository // InMemoryColorsRepository
) : BaseViewModel() {

    private val _currentColor = MutableLiveResult<NamedColor>(PendingResult())
    val currentColor: LiveResult<NamedColor> = _currentColor

    private val colorListener: ColorListener = {
        _currentColor.postValue(SuccessResult(it))
    }

    init {
        colorsRepository.addListener(colorListener)
        load()

        viewModelScope.launch {
            delay(1000)
            Log.d("MyLog", "context1 = $coroutineContext")
            val result = withContext(Dispatchers.Default) {
                Log.d("MyLog", "context2 = $coroutineContext")
                val res1 = async {
                    Log.d("MyLog", "context3 = $coroutineContext")
                    delay(1000)
                    return@async "res1"
                }
                val res2 = async {
                    Log.d("MyLog", "context4 = $coroutineContext")
                    delay(2000)
                    return@async "res2"
                }
                val res3 = async {
                    Log.d("MyLog", "context5 = $coroutineContext")
                    delay(3000)
                    return@async "res3"
                }
                Log.d("MyLog", "$res3")

                val r1 = res1.await()
                val r2 = res2.await()
                val r3 = res3.await()

                return@withContext "$r1\n$r2\n$r3"
            }

            Log.d("MyLog", result)
        }

    }

    override fun onCleared() {
        super.onCleared()
        colorsRepository.removeListener(colorListener)
    }

    override fun onResult(result: Any) {
        super.onResult(result)
        if (result is NamedColor) {
            val message = uiActions.getString(R.string.changed_color, result.name)
            uiActions.toast(message)
        }
    }

    fun changeColor() {
        val currentColor = currentColor.value.takeSuccess() ?: return
        val screen = ChangeColorFragment.Screen(currentColor.id)
        navigator.launch(screen)
    }

    fun onTryAgain() {
        load()
    }

    private fun load() {
        colorsRepository.getCurrentColor().into(_currentColor)
    }

}