package com.alexeyyuditsky.simplemvvm.view.changecolor

import androidx.lifecycle.*
import com.alexeyyuditsky.foundation.model.PendingResult
import com.alexeyyuditsky.foundation.model.SuccessResult
import com.alexeyyuditsky.simplemvvm.R
import com.alexeyyuditsky.simplemvvm.model.colors.ColorsRepository
import com.alexeyyuditsky.simplemvvm.model.colors.NamedColor
import com.alexeyyuditsky.foundation.navigator.Navigator
import com.alexeyyuditsky.foundation.uiactions.UIActions
import com.alexeyyuditsky.foundation.views.BaseViewModel
import com.alexeyyuditsky.foundation.views.LiveResult
import com.alexeyyuditsky.foundation.views.MediatorLiveResult
import com.alexeyyuditsky.foundation.views.MutableLiveResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ChangeColorViewModel(
    screen: ChangeColorFragment.Screen,
    private val navigator: Navigator,
    private val uiActions: UIActions,
    private val colorsRepository: ColorsRepository,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel(), ColorsAdapter.Listener {

    // input sources
    private val _availableColors = MutableLiveResult<List<NamedColor>>(PendingResult())
    private val _currentColorId =
        savedStateHandle.getLiveData("currentColorId", screen.currentColorId)

    // main destination (contains merged values from _availableColors & _currentColorId)
    private val _colorsList = MediatorLiveResult<List<NamedColorListItem>>()
    val colorsList: LiveResult<List<NamedColorListItem>> = _colorsList

    // side destination, also the same result can be achieved by using Transformations.map() function.
    private val _screenTitle = MutableLiveData<String>()
    val screenTitle: LiveData<String> = _screenTitle

    init {
        viewModelScope.launch {
            delay(2000)
            _availableColors.value = SuccessResult(colorsRepository.getAvailableColors())
        }
        // initializing MediatorLiveData
        _colorsList.addSource(_availableColors) { mergeSources() }
        _colorsList.addSource(_currentColorId) { mergeSources() }
    }

    override fun onColorChosen(namedColor: NamedColor) {
        if (_currentColorId.value == namedColor.id) return
        _currentColorId.value = namedColor.id
    }

    fun onSavePressed() {
        val currentColorId = _currentColorId.value ?: return
        val currentColor = colorsRepository.getById(currentColorId)
        colorsRepository.currentColor = currentColor
        navigator.goBack(result = currentColor)
    }

    fun onCancelPressed() = navigator.goBack()

    /** [MediatorLiveData] can listen other LiveData instances (even more than 1) and combine their values.
     * Here we listen the list of available colors ([_availableColors] live-data) + current color id
     * ([_currentColorId] live-data), then we use both of these values in order to create a list of
     * [NamedColorListItem], it is a list to be displayed in RecyclerView. */
    private fun mergeSources() {
        val colors = _availableColors.value ?: return
        val currentColorId = _currentColorId.value ?: return
        _colorsList.value = colors.map { color ->
            color.map { NamedColorListItem(it, it.id == currentColorId) }
        }

        /*_screenTitle.value =
            uiActions.getString(R.string.change_color_screen_title, currentColor.name)*/
    }

}
