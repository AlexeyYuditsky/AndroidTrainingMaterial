package com.alexeyyuditsky.pixabayapp.di

import com.alexeyyuditsky.pixabayapp.data.PhotosCloudDataSource
import com.alexeyyuditsky.pixabayapp.data.PhotosRepository
import com.alexeyyuditsky.pixabayapp.domain.PhotosInteracor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SourcesModule {

    @Binds
    abstract fun bindPhotosCloudDataSource(
        photosCloudDataSource: PhotosCloudDataSource.Base
    ): PhotosCloudDataSource

    @Binds
    abstract fun bindPhotosRepository(
        photosRepository: PhotosRepository.Base
    ): PhotosRepository

    @Binds
    abstract fun bindPhotosInteractor(
        photosInteracor: PhotosInteracor.Base
    ): PhotosInteracor

}