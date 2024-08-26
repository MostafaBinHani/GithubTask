package com.example.voistask.model

import android.util.Log

class UserRepository(private val searchRepo: SearchRepo) {

    // Existing function to fetch users
    suspend fun fetchUsers(username: String, page: Int): List<User> {
        Log.d("UserRepo", "Search clicked for username: $username, page: $page")
        val response = searchRepo.searchUsers(query = username, page = page)

        if (response.isSuccessful) {
            Log.d("UserRepo", "Search response: ${response.body()}")
            return response.body()?.items ?: emptyList()
        } else {
            Log.e("UserRepo", "Failed to fetch users: ${response.code()} - ${response.message()}")
            throw Exception("Failed to fetch users: ${response.code()} - ${response.message()}")
        }
    }

    // New function to fetch user details by login
    suspend fun fetchUserDetails(login: String): User {
        Log.d("UserRepo", "yaraaab $login")
        val response = searchRepo.getUserDetails(login)

        if (response.isSuccessful) {
            return response.body() ?: throw Exception("No user data found")
        } else {
            Log.e("UserRepo", "error ahu: ${response.code()} - ${response.message()}")
            throw Exception("Failed to fetch user details: ${response.code()} - ${response.message()}")
        }
    }
}
