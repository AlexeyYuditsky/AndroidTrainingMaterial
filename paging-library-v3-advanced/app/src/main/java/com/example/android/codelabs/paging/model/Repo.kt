package com.example.android.codelabs.paging.model

import com.squareup.moshi.Json

data class Repo(
    @field:Json(name = "id") val id: Long,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "full_name") val fullName: String,
    @field:Json(name = "description") val description: String?,
    @field:Json(name = "html_url") val url: String?,
    @field:Json(name = "stargazers_count") val stars: Int,
    @field:Json(name = "forks_count") val forks: Int,
    @field:Json(name = "language") val language: String?
)
