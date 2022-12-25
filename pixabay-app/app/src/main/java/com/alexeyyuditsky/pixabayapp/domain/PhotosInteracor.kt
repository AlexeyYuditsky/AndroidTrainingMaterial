package com.alexeyyuditsky.pixabayapp.domain

import com.alexeyyuditsky.pixabayapp.data.PhotoData
import com.alexeyyuditsky.pixabayapp.data.PhotosRepository
import javax.inject.Inject
import javax.inject.Singleton

interface PhotosInteracor {

    suspend fun fetchPhotos(query: String): PhotoData

    @Singleton
    class Base @Inject constructor(
        private val photosRepository: PhotosRepository
    ) : PhotosInteracor {

        override suspend fun fetchPhotos(query: String): PhotoData {
            return photosRepository.fetchPhotos(query)
        }

    }

}