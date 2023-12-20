package com.alexeyyuditsky.composenew

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class MainViewModel : ViewModel() {

    private val initialList = List(500) {
        InstagramModel(
            id = it,
            title = "Title: $it",
            isFollowed = Random.nextBoolean()
        )
    }

    private val _models = MutableLiveData(initialList)
    val models: LiveData<List<InstagramModel>> get() = _models

    fun changeFollowingStatus(model: InstagramModel) {
        _models.value = models.value?.map { instagramModel ->
            if (instagramModel == model) {
                instagramModel.copy(isFollowed = !instagramModel.isFollowed)
            } else {
                instagramModel
            }
        } ?: mutableListOf()
    }

    fun deleteItem(model: InstagramModel) {
        _models.value = models.value?.toMutableList()?.apply {
            remove(model)
        }
    }
}