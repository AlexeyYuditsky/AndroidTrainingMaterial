package ua.cn.stu.http

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

data class RootResponseBody(
    val rates: Rates
)

data class Rates(
    val RUB: String
)

interface API {
    @Headers("X-RapidAPI-Key: gkqOYPoh6yEeC6zNNUOz4bmi562njAYT, ")
    @GET("latest?symbols=RUB&base=USD")
    suspend fun getRates(): RootResponseBody
}

suspend fun main() {
    val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    val moshi = Moshi.Builder().build()
    val moshiConverterFactory = MoshiConverterFactory.create(moshi)

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.apilayer.com/exchangerates_data/")
        .client(client)
        .addConverterFactory(moshiConverterFactory)
        .build()

    val api = retrofit.create(API::class.java)

    val response = api.getRates()

    println(response.rates.RUB)
}