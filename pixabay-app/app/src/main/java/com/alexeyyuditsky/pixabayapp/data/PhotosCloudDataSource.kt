package com.alexeyyuditsky.pixabayapp.data

import javax.inject.Inject
import javax.inject.Singleton

interface PhotosCloudDataSource {

    suspend fun fetchPhotos(query: String): PhotoData

    @Singleton
    class Base @Inject constructor(
        private val pixabayService: PixabayService
    ) : PhotosCloudDataSource {
        override suspend fun fetchPhotos(query: String): PhotoData {
            return pixabayService.fetchPhotos(query = query)
        }
    }

}