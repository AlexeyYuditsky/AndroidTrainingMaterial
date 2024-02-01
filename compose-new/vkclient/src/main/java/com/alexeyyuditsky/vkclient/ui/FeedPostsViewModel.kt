package com.alexeyyuditsky.vkclient.ui

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexeyyuditsky.vkclient.domain.FeedPost
import com.alexeyyuditsky.vkclient.domain.StatisticItem

class FeedPostsViewModel : ViewModel() {

    private val postList = List(10) { FeedPost(id = it, contentText = "con/tent $it") }

    private val initialState = FeedPostScreenState.Posts(postList)

    private val _screenState = MutableLiveData<FeedPostScreenState>(initialState)
    val screenState: LiveData<FeedPostScreenState> get() = _screenState

    fun updateCount(feedPost: FeedPost, statisticItem: StatisticItem) {
        val state = screenState.value
        if (state !is FeedPostScreenState.Posts) return

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

        _screenState.value = FeedPostScreenState.Posts(posts = newPosts)
    }

    fun remove(feedPost: FeedPost) {
        val state = screenState.value
        if (state !is FeedPostScreenState.Posts) return

        val newPosts = state.posts.filter { it != feedPost }
        _screenState.value = FeedPostScreenState.Posts(newPosts)
    }
}