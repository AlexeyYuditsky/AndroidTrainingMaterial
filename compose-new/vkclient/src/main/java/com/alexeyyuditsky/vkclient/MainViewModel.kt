package com.alexeyyuditsky.vkclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexeyyuditsky.vkclient.domain.FeedPost
import com.alexeyyuditsky.vkclient.domain.StatisticItem
import com.alexeyyuditsky.vkclient.ui.theme.NavigationItem

class MainViewModel : ViewModel() {

    private val feedPosts = List(10) { FeedPost(id = it) }

    private val _feedPostList = MutableLiveData(feedPosts)
    val feedPostList: LiveData<List<FeedPost>> get() = _feedPostList

    private val _selectedNavItem = MutableLiveData<NavigationItem>(NavigationItem.Home)
    val selectedNavItem: LiveData<NavigationItem> get() = _selectedNavItem

    fun selectNavItem(navItem: NavigationItem) {
        _selectedNavItem.value = navItem
    }

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

    fun remove(feedPost: FeedPost) {
        _feedPostList.value = feedPostList.value?.filter { it != feedPost }
    }
}