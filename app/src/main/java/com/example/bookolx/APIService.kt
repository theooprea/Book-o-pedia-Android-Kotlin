package com.example.bookolx

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.*

private const val BASE_URL = "http://192.168.100.15:3000"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .build()

interface APIService {

    @POST("/auth/login")
    suspend fun login(@Body requestBody: RequestBody) : Response<ResponseBody>

    @POST("/auth/register")
    suspend fun register(@Body requestBody: RequestBody) : Response<ResponseBody>

    @GET("/users/{username}")
    suspend fun getUser(@Path("username") username: String, @HeaderMap headers: Map<String, String>): Response<ResponseBody>

    @GET("/users/{username}/books")
    suspend fun getUserBooks(@Path("username") username: String, @HeaderMap headers: Map<String, String>): Response<ResponseBody>

    @POST("/users/{username}/books")
    suspend fun addUserBook(@Path("username") username: String, @HeaderMap headers: Map<String, String>, @Body requestBody: RequestBody): Response<ResponseBody>

    @DELETE("/users/{username}/books/{title}")
    suspend fun deleteUserBook(@Path("username") username: String, @Path("title") title: String, @HeaderMap headers: Map<String, String>): Response<ResponseBody>

    @PUT("/users/{username}/books/{title}")
    suspend fun editUserBook(@Path("username") username: String, @Path("title") title: String, @HeaderMap headers: Map<String, String>, @Body requestBody: RequestBody): Response<ResponseBody>

    @GET("/users/{username}/wishList")
    suspend fun getUserWishList(@Path("username") username: String, @HeaderMap headers: Map<String, String>): Response<ResponseBody>

    @DELETE("/users/{username}/wishList/{title}")
    suspend fun deleteUserWishlistBook(@Path("username") username: String, @Path("title") title: String, @HeaderMap headers: Map<String, String>): Response<ResponseBody>

    @PUT("/users/{username}")
    suspend fun editUser(@Path("username") username: String, @HeaderMap headers: Map<String, String>, @Body requestBody: RequestBody): Response<ResponseBody>

    @GET("/books")
    suspend fun getBooks(@HeaderMap headers: Map<String, String>): Response<ResponseBody>
}

object BookApi {
    val retrofitService : APIService by lazy { retrofit.create(APIService::class.java) }
}