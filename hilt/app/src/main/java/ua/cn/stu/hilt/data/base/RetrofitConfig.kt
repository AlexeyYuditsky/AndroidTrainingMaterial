package ua.cn.stu.hilt.data.base

import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * All stuffs required for making HTTP-requests with Retrofit client and
 * for parsing JSON-messages.
 */
@Singleton
class RetrofitConfig @Inject constructor(
    val retrofit: Retrofit,
    val moshi: Moshi,
)