package com.example.voistask.model

import retrofit2.Response

class SearchRepo {
    private val apiService: ApiService by lazy {
        RetrofitInstance.retrofit.create(ApiService::class.java)
    }

    suspend fun searchUsers(query: String, page: Int): Response<SearchResponse> {
        return apiService.searchUsers(query, page)
    }

    suspend fun getUserDetails(login: String): Response<User> {
        return apiService.getUserDetails(login)
    }
}
