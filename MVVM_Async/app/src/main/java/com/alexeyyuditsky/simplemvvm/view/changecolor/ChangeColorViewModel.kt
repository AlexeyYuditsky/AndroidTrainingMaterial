package com.alexeyyuditsky.simplemvvm.view.changecolor

import androidx.lifecycle.*
import com.alexeyyuditsky.foundation.model.ErrorResult
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
    private val _currentColorId = savedStateHandle.getLiveData("currColorId", screen.currentColorId)
    private val _saveInProgress = MutableLiveData(false)

    // main destination (contains merged values from _availableColors & _currentColorId)
    private val _viewState = MediatorLiveResult<ViewState>()
    val viewState: LiveResult<ViewState> = _viewState

    // side destination, also the same result can be achieved by using Transformations.map() function.
    val screenTitle: LiveData<String> = Transformations.map(viewState) { result ->
        if (result is SuccessResult) {
            val currentColor = result.data.colorsList.first { it.selected }
            uiActions.getString(R.string.change_color_screen_title, currentColor.namedColor.name)
        } else {
            uiActions.getString(R.string.change_color_screen_title_simple)
        }
    }

    var mockError = true

    init {
        viewModelScope.launch {
            delay(1000)
            _availableColors.value = SuccessResult(colorsRepository.getAvailableColors())
        }
        _viewState.addSource(_availableColors) { mergeSources() }
        _viewState.addSource(_currentColorId) { mergeSources() }
        _viewState.addSource(_saveInProgress) { mergeSources() }
    }

    override fun onColorChosen(namedColor: NamedColor) {
        if (_saveInProgress.value == true) return
        if (_currentColorId.value == namedColor.id) return
        _currentColorId.value = namedColor.id
    }

    fun onSavePressed() {
        viewModelScope.launch {
            _saveInProgress.postValue(true)
            delay(1000)
            if (mockError) {
                _saveInProgress.postValue(false)
                uiActions.toast("Error")
                mockError = false
            } else {
                _saveInProgress.postValue(true)
                delay(1000)
                val currentColorId = _currentColorId.value ?: return@launch
                val currentColor = colorsRepository.getById(currentColorId)
                colorsRepository.currentColor = currentColor
                navigator.goBack(result = currentColor)
            }
        }
    }

    fun onCancelPressed() = navigator.goBack()

    fun tryAgain() {
        viewModelScope.launch {
            _availableColors.postValue(PendingResult())
            delay(1000)
            _availableColors.postValue(SuccessResult(colorsRepository.getAvailableColors()))
        }
    }

    /** [MediatorLiveData] can listen other LiveData instances (even more than 1) and combine their values.
     * Here we listen the list of available colors ([_availableColors] live-data) + current color id
     * ([_currentColorId] live-data), then we use both of these values in order to create a list of
     * [NamedColorListItem], it is a list to be displayed in RecyclerView. */
    private fun mergeSources() {
        val colors = _availableColors.value ?: return
        val currentColorId = _currentColorId.value ?: return
        val saveInProgress = _saveInProgress.value ?: return

        _viewState.value = colors.map { colorList ->
            ViewState(
                colorsList = colorList.map { NamedColorListItem(it, it.id == currentColorId) },
                showSaveButton = !saveInProgress,
                showCancelButton = !saveInProgress,
                showSaveProgressBar = saveInProgress
            )
        }
    }

    data class ViewState(
        val colorsList: List<NamedColorListItem>,
        val showSaveButton: Boolean,
        val showCancelButton: Boolean,
        val showSaveProgressBar: Boolean
    )

}

