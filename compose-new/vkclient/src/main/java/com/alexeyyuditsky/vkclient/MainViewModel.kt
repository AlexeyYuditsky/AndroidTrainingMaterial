package com.alexeyyuditsky.vkclient

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexeyyuditsky.vkclient.domain.FeedPost
import com.alexeyyuditsky.vkclient.domain.PostComment
import com.alexeyyuditsky.vkclient.domain.StatisticItem
import com.alexeyyuditsky.vkclient.ui.HomeScreenState
import com.alexeyyuditsky.vkclient.ui.NavigationItem

class MainViewModel : ViewModel() {

    private val commentList = List(10) { PostComment(id = it) }
    private val postList = List(10) { FeedPost(id = it) }

    private val initialState = HomeScreenState.Posts(postList)

    private val _screenState = MutableLiveData<HomeScreenState>(initialState)
    val screenState: LiveData<HomeScreenState> get() = _screenState

    private val _selectedNavItem = MutableLiveData<NavigationItem>(NavigationItem.Home)
    val selectedNavItem: LiveData<NavigationItem> get() = _selectedNavItem

    private var savedState: HomeScreenState = initialState

    fun selectNavItem(navItem: NavigationItem) {
        _selectedNavItem.value = navItem
    }

    fun showComments(feedPost: FeedPost) {
        savedState = _screenState.value ?: return

        _screenState.value = HomeScreenState.Comments(
            feedPost = feedPost,
            comments = commentList
        )
    }

    fun closeCommentScreen() {
        _screenState.value = savedState
    }

    fun updateCount(feedPost: FeedPost, statisticItem: StatisticItem) {
        val state = screenState.value
        if (state !is HomeScreenState.Posts) return

        val oldFeedPostList = state.feedPosts.toMutableList()

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

        _screenState.value = HomeScreenState.Posts(feedPosts = newPosts)
    }

    fun remove(feedPost: FeedPost) {
        val state = screenState.value
        if (state !is HomeScreenState.Posts) return
        val newPosts = state.feedPosts.filter { it != feedPost }
        _screenState.value = HomeScreenState.Posts(newPosts)
    }
}