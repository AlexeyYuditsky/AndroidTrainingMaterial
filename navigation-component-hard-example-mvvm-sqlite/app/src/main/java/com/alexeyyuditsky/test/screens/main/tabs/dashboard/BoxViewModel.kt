package com.alexeyyuditsky.test.screens.main.tabs.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexeyyuditsky.test.model.boxes.BoxesRepository
import com.alexeyyuditsky.test.utils.Event
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class BoxViewModel(
    boxId: Long,
    boxesRepository: BoxesRepository
) : ViewModel() {

    private val _shouldExitEvent = MutableLiveData<Event<Boolean>>()
    val shouldExitEvent: LiveData<Event<Boolean>> = _shouldExitEvent

    init {
        viewModelScope.launch {
            boxesRepository.getBoxes(onlyActive = true)
                .map { boxes -> boxes.firstOrNull { it.id == boxId } }
                .collect { currentBox -> _shouldExitEvent.value = Event(currentBox == null) }
        }
    }

}