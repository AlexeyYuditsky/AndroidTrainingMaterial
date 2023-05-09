package com.alexeyyuditsky.news.data.retrofit

import com.alexeyyuditsky.news.presentation.base.Const
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String = "ru",
        @Query("page")
        pageNumber: Int = 1,
        @Query("pageSize")
        pageSize: Int = Const.QUERY_PAGE_SIZE,
        @Query("apiKey")
        apiKey: String = Const.API_KEY
    ): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("pageSize")
        pageSize: Int = Const.QUERY_PAGE_SIZE,
        @Query("apiKey")
        apiKey: String = Const.API_KEY
    ): NewsResponse

    companion object {
        fun create(): NewsApi {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            val moshi = Moshi.Builder()
                .build()

            return Retrofit.Builder()
                .baseUrl("https://newsapi.org/")
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(NewsApi::class.java)
        }
    }

}