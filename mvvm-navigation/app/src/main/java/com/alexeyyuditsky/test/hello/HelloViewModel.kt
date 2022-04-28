package com.alexeyyuditsky.test.hello

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.base.BaseViewModel
import com.alexeyyuditsky.test.edit.EditFragment
import com.alexeyyuditsky.test.navigator.Navigator

class HelloViewModel(
    private val navigator: Navigator,
    screen: HelloFragment.Screen
) : BaseViewModel() { // базовый интерфейс вложенных классов

    // свойства в которых храним содержимое valueTextView
    private val _currentMessageLiveData = MutableLiveData<String>()
    val currentMessageLiveData: LiveData<String> = _currentMessageLiveData

    init {
        // устанавливаем значение по умолчанию для valueTextView либо устанавливаем его в xml
        _currentMessageLiveData.value = navigator.getString(R.string.hello_world)
    }

    // метод который слушает результат из экрана EditFragment
    override fun onResult(result: Any) {
        super.onResult(result)
        if (result is String) {
            _currentMessageLiveData.value = result
        }
    }

    // создаём и передаём в EditViewModel вложенный объект EditFragment.Screen() с текущим значением valueTextView
    fun onEditPressed() {
        navigator.launch(EditFragment.Screen(initialValue = currentMessageLiveData.value!!))
    }

}