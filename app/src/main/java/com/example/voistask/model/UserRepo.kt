package com.example.voistask.model

import android.util.Log
import kotlin.math.log

class UserRepository(private val searchRepo: SearchRepo) {

    // Function to fetch users using the API service
    suspend fun fetchUsers(username: String, page: Int): List<User> {
        Log.d("UserRepo", "Search clicked for username: $username, page: $page")
        val response = searchRepo.searchUsers(query = username, page = page)

        if (response.isSuccessful) {
            Log.d("UserRepo", "Search response: ${response.body()}")
            return response.body()?.items ?: emptyList()
        } else {
            Log.e("UserReposs", "Failed to fetch users: ${response.code()} - ${response.message()}")
            Log.e("UserReposs", "Response body: ${response.errorBody()?.string()}")
            throw Exception("Failed to fetch users: ${response.code()} - ${response.message()}")
        }
    }

}
