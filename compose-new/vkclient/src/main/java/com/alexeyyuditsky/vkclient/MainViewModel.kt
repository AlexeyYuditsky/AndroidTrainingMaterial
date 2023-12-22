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

    }
}