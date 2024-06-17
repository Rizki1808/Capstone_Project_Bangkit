package com.example.capstoneproject.data.tools

import android.util.Log
import com.example.capstoneproject.data.api.ApiConfig
import com.example.capstoneproject.data.api.ApiService
import com.example.capstoneproject.data.response.DiseasesResponse
import com.example.capstoneproject.data.response.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class UserRepository private constructor(
    private var apiService: ApiService,
    private val userPreference: UserPreference
) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun getDiseases(): DiseasesResponse {
        val session = userPreference.getSession().first()
        Log.d("UserRepository", "Fetching stories with token: ${session.token}")
        val apiServiceWithToken = ApiConfig.getApiService(session.token)
        return apiServiceWithToken.getDiseases()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(userPreference: UserPreference, apiService: ApiService): UserRepository {
            return instance ?: synchronized(this) {
                UserRepository(apiService, userPreference).also { instance = it }
            }
        }
    }
}