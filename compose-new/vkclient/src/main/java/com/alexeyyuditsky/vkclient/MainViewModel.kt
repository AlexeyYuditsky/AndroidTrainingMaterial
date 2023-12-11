package com.alexeyyuditsky.vkclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexeyyuditsky.vkclient.domain.FeedPost
import com.alexeyyuditsky.vkclient.domain.StatisticItem

class MainViewModel : ViewModel() {

    private val _feedPost = MutableLiveData(FeedPost())
    val feedPost: LiveData<FeedPost> get() = _feedPost

    fun update(newStatisticItem: StatisticItem) {
        val oldStatistic = _feedPost.value?.statistics ?: throw IllegalStateException()
        val newStatistic = oldStatistic.map { oldStatisticItem ->
            if (oldStatisticItem.type == newStatisticItem.type) {
                oldStatisticItem.copy(count = oldStatisticItem.count + 1)
            } else {
                oldStatisticItem
            }
        }

        _feedPost.value = feedPost.value?.copy(statistics = newStatistic)
    }
}