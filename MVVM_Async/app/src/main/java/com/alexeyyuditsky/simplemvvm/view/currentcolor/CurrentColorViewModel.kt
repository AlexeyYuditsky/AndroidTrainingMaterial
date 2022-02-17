package com.alexeyyuditsky.simplemvvm.view.currentcolor

import androidx.lifecycle.viewModelScope
import com.alexeyyuditsky.foundation.model.ErrorResult
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
        viewModelScope.launch {
            delay(2000L)
            //_currentColor.postValue(ErrorResult(RuntimeException()))
            colorsRepository.addListener(colorListener)  // example of listening results via model layer
        }
    }

    override fun onCleared() {
        super.onCleared()
        colorsRepository.removeListener(colorListener)
    }

    // example of listening results directly from the screen
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
        viewModelScope.launch {
            _currentColor.value = PendingResult()
            delay(2000)
            colorsRepository.addListener(colorListener)
        }
    }

}