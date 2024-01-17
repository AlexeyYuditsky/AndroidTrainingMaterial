package com.alexeyyuditsky.vkclient.ui

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexeyyuditsky.vkclient.domain.FeedPost
import com.alexeyyuditsky.vkclient.domain.StatisticItem

class FeedPostsViewModel : ViewModel() {

    private val postList = List(10) { FeedPost(id = it) }

    private val _screenState = MutableLiveData<HomeScreenState>(HomeScreenState.Initial)
    val screenState: LiveData<HomeScreenState> get() = _screenState

    private val _selectedNavItem = MutableLiveData<NavigationItem>(NavigationItem.Home)
    val selectedNavItem: LiveData<NavigationItem> get() = _selectedNavItem

    fun selectNavItem(navItem: NavigationItem) {
        _selectedNavItem.value = navItem
    }

    fun updateCount(feedPost: FeedPost, statisticItem: StatisticItem) {
        val state = screenState.value
        if (state !is HomeScreenState.Posts) return

        val oldFeedPostList = state.posts.toMutableList()

        val newStatistics = feedPost.statistics.map {
            if (statisticItem.type == it.type) {
                it.copy(count = it.count + 1)
            } else {
                it
            }
        }

        val newFeedPost = feedPost.copy(statistics = newStatistics)

        val newPosts = oldFeedPostList.apply {
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

        _screenState.value = HomeScreenState.Posts(posts = newPosts)
    }

    fun remove(feedPost: FeedPost) {
        val state = screenState.value
        if (state !is HomeScreenState.Posts) return

        val newPosts = state.posts.filter { it != feedPost }
        _screenState.value = HomeScreenState.Posts(newPosts)
    }
}