package com.alexeyyuditsky.vkclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexeyyuditsky.vkclient.domain.FeedPost
import com.alexeyyuditsky.vkclient.domain.StatisticItem

class MainViewModel : ViewModel() {

    private val feedPosts = List(10) { FeedPost(id = it) }

    private val _feedPostList = MutableLiveData(feedPosts)
    val feedPostList: LiveData<List<FeedPost>> get() = _feedPostList

    fun updateCount(feedPost: FeedPost, statisticItem: StatisticItem) {
        val oldFeedPostList = _feedPostList.value ?: return

        val oldFeedPost = oldFeedPostList.find { it.id == feedPost.id } ?: return

        val newStatistics = oldFeedPost.statistics.map {
            if (statisticItem.type == it.type) {
                it.copy(count = it.count + 1)
            } else {
                it
            }
        }

        val newFeedPost = oldFeedPost.copy(statistics = newStatistics)

        val newFeedPostList = oldFeedPostList.map {
            if (feedPost.id == it.id) {
                newFeedPost
            } else {
                it
            }
        }

        _feedPostList.value = newFeedPostList
    }
}