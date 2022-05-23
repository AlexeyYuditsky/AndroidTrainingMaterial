package com.alexeyyuditsky.test.screens.main.tabs.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.model.StorageException
import com.alexeyyuditsky.test.model.boxes.BoxesRepository
import com.alexeyyuditsky.test.model.boxes.entities.Box
import com.alexeyyuditsky.test.utils.Event
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val boxesRepository: BoxesRepository
) : ViewModel(), SettingsAdapter.Listener {

    private val _boxSettings = MutableLiveData<List<BoxSetting>>()
    val boxSettings: LiveData<List<BoxSetting>> = _boxSettings

    private val _showErrorMessageEvent = MutableLiveData<Event<Int>>()
    val showErrorMessageEvent: LiveData<Event<Int>> = _showErrorMessageEvent

    init {
        viewModelScope.launch {
            val allBoxesFlow = boxesRepository.getBoxes(onlyActive = false)
            val activeBoxesFlow = boxesRepository.getBoxes(onlyActive = true)
            val boxSettingsFlow = combine(allBoxesFlow, activeBoxesFlow) { allBoxes, activeBoxes ->
                allBoxes.map { BoxSetting(box = it, enabled = activeBoxes.contains(it)) }
            }
            boxSettingsFlow.collect {
                _boxSettings.value = it
            }
        }
    }

    override fun enableBox(box: Box) {
        viewModelScope.launch {
            try {
                boxesRepository.activateBox(box)
            } catch (e: StorageException) {
                showStorageErrorMessage()
            }
        }
    }

    override fun disableBox(box: Box) {
        viewModelScope.launch {
            try {
                boxesRepository.deactivateBox(box)
            } catch (e: StorageException) {
                showStorageErrorMessage()
            }
        }
    }

    private fun showStorageErrorMessage() {
        _showErrorMessageEvent.value = Event(R.string.storage_error)
    }

}