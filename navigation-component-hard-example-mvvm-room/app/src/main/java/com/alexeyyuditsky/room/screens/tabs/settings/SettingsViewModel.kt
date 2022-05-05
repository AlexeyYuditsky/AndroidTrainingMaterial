package com.alexeyyuditsky.room.screens.tabs.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexeyyuditsky.room.R
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import com.alexeyyuditsky.room.model.StorageException
import com.alexeyyuditsky.room.model.boxes.BoxesRepository
import com.alexeyyuditsky.room.model.boxes.entities.Box
import com.alexeyyuditsky.room.model.boxes.entities.BoxAndSettings
import com.alexeyyuditsky.room.screens.tabs.settings.SettingsAdapter
import com.alexeyyuditsky.room.utils.MutableLiveEvent
import com.alexeyyuditsky.room.utils.publishEvent
import com.alexeyyuditsky.room.utils.share

class SettingsViewModel(
    private val boxesRepository: BoxesRepository
) : ViewModel(), SettingsAdapter.Listener {

    private val _boxSettings = MutableLiveData<List<BoxAndSettings>>()
    val boxSettings = _boxSettings.share()

    private val _showErrorMessageEvent = MutableLiveEvent<Int>()
    val showErrorMessageEvent =_showErrorMessageEvent.share()

    init {
        viewModelScope.launch {
            boxesRepository.getBoxesAndSettings().collect {
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
        _showErrorMessageEvent.publishEvent(R.string.storage_error)
    }
}