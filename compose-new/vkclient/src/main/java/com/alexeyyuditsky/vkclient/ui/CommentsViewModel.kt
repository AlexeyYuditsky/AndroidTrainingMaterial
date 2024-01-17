package com.alexeyyuditsky.vkclient.ui

import androidx.lifecycle.ViewModel
import com.alexeyyuditsky.vkclient.domain.PostComment

class CommentsViewModel : ViewModel() {

    val commentList = List(10) { PostComment(id = it) }



}