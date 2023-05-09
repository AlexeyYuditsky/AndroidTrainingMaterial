package com.alexeyyuditsky.pixabayapp.data

import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayService {

    @GET("/api")
    suspend fun fetchPhotos(
        @Query("q") query: String
    ): PhotoData

}