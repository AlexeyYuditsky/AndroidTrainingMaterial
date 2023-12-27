package com.alexeyyuditsky.vkclient

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexeyyuditsky.vkclient.domain.FeedPost
import com.alexeyyuditsky.vkclient.domain.StatisticItem
import com.alexeyyuditsky.vkclient.ui.theme.NavigationItem

class MainViewModel : ViewModel() {

    private val sourceList = List(10) { FeedPost(id = it) }

    private val _feedPostList = MutableLiveData(sourceList)
    val feedPostList: LiveData<List<FeedPost>> get() = _feedPostList

    private val _selectedNavItem = MutableLiveData<NavigationItem>(NavigationItem.Home)
    val selectedNavItem: LiveData<NavigationItem> get() = _selectedNavItem

    fun selectNavItem(navItem: NavigationItem) {
        _selectedNavItem.value = navItem
    }

    fun updateCount(feedPost: FeedPost, statisticItem: StatisticItem) {
        val oldFeedPostList = _feedPostList.value?.toMutableList() ?: return

        val newStatistics = feedPost.statistics.map {
            if (statisticItem.type == it.type) {
                it.copy(count = it.count + 1)
            } else {
                it
            }
        }

        val newFeedPost = feedPost.copy(statistics = newStatistics)

        _feedPostList.value = oldFeedPostList.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                replaceAll {
                    if (feedPost.id == it.id) {
                        newFeedPost
                    } else {
                        it
                    }
                }
            } else {
                oldFeedPostList.forEachIndexed { index, it ->
                    if (feedPost.id == it.id) {
                        oldFeedPostList[index] = newFeedPost
                    }
                }
            }
        }
    }

    fun remove(feedPost: FeedPost) {
        _feedPostList.value = feedPostList.value?.filter { it != feedPost }
    }
}