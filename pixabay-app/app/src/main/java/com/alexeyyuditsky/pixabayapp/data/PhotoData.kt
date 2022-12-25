package com.alexeyyuditsky.pixabayapp.data

import com.google.gson.annotations.SerializedName

data class PhotoData(
    @SerializedName("total")
    val total: String,
    @SerializedName("totalHits")
    val totalHits: String,
    @SerializedName("hits")
    val hits: List<Photo>
)

data class Photo(
    @SerializedName("webformatURL")
    val imageUrl: String,
    @SerializedName("user")
    val user: String,
    @SerializedName("likes")
    val likes: String,
    @SerializedName("comments")
    val comments: String
)