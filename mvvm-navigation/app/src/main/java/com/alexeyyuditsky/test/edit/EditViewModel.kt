package com.alexeyyuditsky.test.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alexeyyuditsky.test.Event
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.base.BaseViewModel
import com.alexeyyuditsky.test.navigator.Navigator

class EditViewModel(
    private val navigator: Navigator,
    screen: EditFragment.Screen
) : BaseViewModel() {

    // поле которое прослушивается фрагментом только один раз, поэтому оно имеет тип Event
    private val _initialMessageEvent = MutableLiveData<Event<String>>()
    val initialMessageEvent: LiveData<Event<String>> = _initialMessageEvent

    init {
        // при создании viewModel сохраняем пришедшие данные из экрана HelloFragment в valueEditText
        _initialMessageEvent.value = Event(screen.initialValue)
    }

    fun onSavePressed(message: String) {
        if (message.isBlank()) {
            navigator.toast(R.string.empty_message)
            return
        }
        navigator.goBack(message)
    }

    fun onCancelPressed() {
        navigator.goBack()
    }

}