package com.example.voistask.model

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String,
        @Query("page") page: Int
    ): Response<SearchResponse>
    @GET("users/{login}")
    suspend fun getUserDetails(
        @Path("login") login: String
    ): Response<User>
}
