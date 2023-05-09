package com.alexeyyuditsky.pixabayapp.di

import com.alexeyyuditsky.pixabayapp.core.Const
import com.alexeyyuditsky.pixabayapp.data.PixabayService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(createApiKeyInterceptor())
            .addNetworkInterceptor(createLoggingInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Const.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun providePixabayService(retrofit: Retrofit): PixabayService {
        return retrofit.create(PixabayService::class.java)
    }

    private fun createApiKeyInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val newHttpUrl = originalRequest.url.newBuilder()
                .addQueryParameter("key", Const.API_KEY)
                .build()
            val newRequest = originalRequest.newBuilder()
                .url(newHttpUrl)
                .build()
            return@Interceptor chain.proceed(newRequest)
        }
    }

    private fun createLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BASIC)
    }

}