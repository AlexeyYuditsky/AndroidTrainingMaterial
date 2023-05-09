package ua.cn.stu.http

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

data class SignInRequestBody(
    val email: String,
    val password: String
)

data class SignInResponseBody(
    val token: String
)

interface API {
    @POST("sign-in")
    suspend fun signIn(@Body signInRequestBody: SignInRequestBody): SignInResponseBody
}

suspend fun main() {
    val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    val moshi = Moshi.Builder().build()
    val moshiConverterFactory = MoshiConverterFactory.create(moshi)

    val retrofit = Retrofit.Builder()
        .baseUrl("http://127.0.0.1:12345")
        .client(client)
        .addConverterFactory(moshiConverterFactory)
        .build()

    val api = retrofit.create(API::class.java)

    val requestBody = SignInRequestBody(
        email = "admin@google.com",
        password = "123"
    )

    val response = api.signIn(requestBody)

    println("TOKEN ${response.token}")
}