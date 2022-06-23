package ua.cn.stu.http

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor

val contentType = "application/json; charset=utf-8".toMediaType()

fun main() {
    val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    val gson = Gson()

    val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val request = Request.Builder()
        .get()
        .url("https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/2022-06-23/currencies/usd.json")
        .build()

    val call = client.newCall(request)

    val response = call.execute()

    val responseBodyString = response.body!!.string()
    println(responseBodyString)
}