package com.example.bookolx

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST

private const val BASE_URL = "http://192.168.100.15:3000"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .build()

interface APIService {

    @POST("/auth/login")
    suspend fun login(@Body requestBody: RequestBody) : Response<ResponseBody>
}

object BookApi {
    val retrofitService : APIService by lazy { retrofit.create(APIService::class.java) }
}