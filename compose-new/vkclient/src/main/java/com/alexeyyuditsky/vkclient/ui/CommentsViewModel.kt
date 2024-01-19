package com.alexeyyuditsky.vkclient.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexeyyuditsky.vkclient.domain.FeedPost
import com.alexeyyuditsky.vkclient.domain.PostComment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CommentsViewModel : ViewModel() {

    private val _screenState = MutableLiveData<CommentsScreenState>(CommentsScreenState.Initial)
    val screenState: LiveData<CommentsScreenState> get() = _screenState

    init {
        loadComments(FeedPost(0))
    }

    fun loadComments(feedPost: FeedPost) {
        val comments = List(10) { PostComment(id = it) }

        _screenState.value = CommentsScreenState.Comments(
            feedPost = feedPost,
            comments = comments
        )
    }
}

fun main() {
    println(setOf("a", "b", false, true, 0))
}