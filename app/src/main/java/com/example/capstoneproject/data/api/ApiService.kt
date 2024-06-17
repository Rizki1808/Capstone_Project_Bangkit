package com.example.capstoneproject.data.api

import com.example.capstoneproject.data.response.DiseasesResponse
import com.example.capstoneproject.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<UserResponse>

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("full_name") name: String,
        @Field("date_of_birth") dateOfBirth: String,
        @Field("gender") gender: String
    ): Call<UserResponse>

    @GET("diseases")
    suspend fun getDiseases(): DiseasesResponse
}