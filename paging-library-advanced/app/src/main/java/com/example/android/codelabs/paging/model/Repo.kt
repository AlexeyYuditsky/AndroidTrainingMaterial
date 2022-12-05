package com.example.android.codelabs.paging.model

import com.squareup.moshi.Json

data class Repo(
    val id: Long,
    val name: String,
    val description: String?,
    val language: String?,
    @field:Json(name = "full_name") val fullName: String,
    @field:Json(name = "html_url") val url: String?,
    @field:Json(name = "stargazers_count") val stars: Int,
    @field:Json(name = "forks_count") val forks: Int
)
