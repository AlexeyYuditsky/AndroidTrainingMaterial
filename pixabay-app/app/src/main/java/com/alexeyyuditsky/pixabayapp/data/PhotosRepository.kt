package com.alexeyyuditsky.pixabayapp.data

import javax.inject.Inject
import javax.inject.Singleton

interface PhotosRepository {

    suspend fun fetchPhotos(query: String): PhotoData

    @Singleton
    class Base @Inject constructor(
        private val photosCloudDataSource: PhotosCloudDataSource
    ) : PhotosRepository {

        override suspend fun fetchPhotos(query: String): PhotoData = try {
            photosCloudDataSource.fetchPhotos(query)
        } catch (e: Exception) {
            throw e
        }

    }

}