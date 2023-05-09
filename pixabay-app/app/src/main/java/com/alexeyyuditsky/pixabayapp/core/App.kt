package com.alexeyyuditsky.pixabayapp.core

import android.app.Application
import com.alexeyyuditsky.pixabayapp.data.PhotosCloudDataSource
import com.alexeyyuditsky.pixabayapp.data.PhotosRepository
import com.alexeyyuditsky.pixabayapp.data.PixabayService
import com.alexeyyuditsky.pixabayapp.domain.PhotosInteracor
import com.alexeyyuditsky.pixabayapp.presentation.MainViewModel
import dagger.hilt.android.HiltAndroidApp
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@HiltAndroidApp
class App : Application()