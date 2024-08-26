package com.example.voistask.model

import com.google.gson.annotations.SerializedName

data class User(
    val login: String,
    val id: Int,
    val name: String?,
    val location: String?,
    val bio: String?,
    val followers: Int?,
    val following: Int?,
    @SerializedName("public_repos") val publicRepos: Int?,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("html_url") val htmlUrl: String
)

data class SearchResponse(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("incomplete_results") val incompleteResults: Boolean,
    val items: List<User>
)