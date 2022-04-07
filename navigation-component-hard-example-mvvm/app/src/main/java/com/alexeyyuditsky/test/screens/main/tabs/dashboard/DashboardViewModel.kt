package com.alexeyyuditsky.test.screens.main.tabs.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexeyyuditsky.test.model.boxes.BoxesRepository
import com.alexeyyuditsky.test.model.boxes.entities.Box

class DashboardViewModel(
    boxesRepository: BoxesRepository
) : ViewModel() {

    private val _boxes = MutableLiveData<List<Box>>()
    val boxes: LiveData<List<Box>> = _boxes

    init {

    }

}