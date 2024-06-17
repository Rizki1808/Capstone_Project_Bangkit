package com.example.capstoneproject.data.response

import com.google.gson.annotations.SerializedName
data class UserResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("loginResult")
    val loginResult: LoginResult?
)


data class LoginResult(
    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("token")
    val token: String
)


data class UserModel(
    val email: String,
    val token: String,
    val isLogin: Boolean = false
)