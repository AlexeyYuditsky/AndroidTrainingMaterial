package ua.cn.stu.http

import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

data class Root(val usd: Rate) {
    val rub: String get() = usd.rub
    val eur: String get() = usd.eur
    val gbp: String get() = usd.gbp
}

data class Rate(
    val rub: String,
    val eur: String,
    val gbp: String
)

interface RetrofitApi {
    @GET("usd.json")
    suspend fun getRates(): Root
}

suspend fun main() {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    val moshi = Moshi.Builder().build()
    val moshiConverterFactory = MoshiConverterFactory.create(moshi)

    val client = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/")
        .client(client)
        .addConverterFactory(moshiConverterFactory)
        .build()

    val api = retrofit.create(RetrofitApi::class.java)

    val response = api.getRates()

    println(
        "rub: ${response.rub}\n" +
                "eur: ${response.eur}\n" +
                "gpb: ${response.gbp}\n"
    )
}