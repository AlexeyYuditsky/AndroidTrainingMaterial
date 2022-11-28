package com.example.android.codelabs.paging.api

import com.example.android.codelabs.paging.model.Repo
import com.squareup.moshi.Json

data class RepoSearchResponse(
    @field:Json(name = "total_count") val total: Int = 0,
    @field:Json(name = "items") val items: List<Repo> = emptyList()
)
