package com.alexeyyuditsky.room.screens.tabs.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexeyyuditsky.room.model.boxes.BoxesRepository
import com.alexeyyuditsky.room.utils.MutableLiveEvent
import com.alexeyyuditsky.room.utils.publishEvent
import com.alexeyyuditsky.room.utils.share
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class BoxViewModel(
    private val boxId: Long,
    private val boxesRepository: BoxesRepository
) : ViewModel() {

    private val _shouldExitEvent = MutableLiveEvent<Boolean>()
    val shouldExitEvent = _shouldExitEvent.share()

    init {
        viewModelScope.launch {
            boxesRepository.getBoxesAndSettings(onlyActive = true)
                .map { boxes -> boxes.firstOrNull { it.box.id == boxId } }
                .collect { currentBox ->
                    _shouldExitEvent.publishEvent(currentBox == null)
                }
        }
    }

}