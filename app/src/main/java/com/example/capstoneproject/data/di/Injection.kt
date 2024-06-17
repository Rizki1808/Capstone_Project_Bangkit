package com.example.capstoneproject.data.di

import android.content.Context
import android.util.Log
import com.example.capstoneproject.data.tools.UserPreference
import com.example.capstoneproject.data.tools.UserRepository
import com.example.capstoneproject.data.api.ApiConfig
import com.example.capstoneproject.data.tools.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        Log.d("Injection", "Token retrieved: ${user.token}")
        val apiService = ApiConfig.getApiService(user.token)
        return UserRepository.getInstance(pref, apiService)
    }
}