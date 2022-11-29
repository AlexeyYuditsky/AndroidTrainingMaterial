package com.example.android.codelabs.paging.api

import com.example.android.codelabs.paging.model.Repo
import com.squareup.moshi.Json

data class RepoSearchResponse(
    val items: List<Repo> = emptyList(),
    @field:Json(name = "total_count") val total: Int = 0,
)
