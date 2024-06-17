package com.example.capstoneproject.ui.login.signin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.data.tools.UserRepository
import com.example.capstoneproject.data.api.ApiConfig
import com.example.capstoneproject.data.response.UserModel
import com.example.capstoneproject.data.response.UserResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInViewModel(private val repository: UserRepository) : ViewModel()  {

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _successMessage = MutableLiveData<String>()
    val successMessage: LiveData<String> = _successMessage

    val session: LiveData<UserModel> = repository.getSession().asLiveData()

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
            Log.d("SignInViewModel", "Session saved: $user")
        }
    }

    fun signIn(email: String, password: String) {
        _successMessage.value = "" // Reset success message
        _errorMessage.value = "" // Reset error message
        val client = ApiConfig.getApiService().login(email, password)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                val userResponse = response.body()
                if (response.isSuccessful && userResponse != null) {
                    if (!userResponse.error) {
                        val loginResult = userResponse.loginResult
                        val token = loginResult?.token
                        if (token != null) {
                            Log.d("SignIn", "Token received: $token")
                            saveSession(UserModel(email, token, true))
                            _successMessage.value = userResponse.message
                        }
                    } else {
                        _errorMessage.value = userResponse.message
                    }
                } else {
                    _errorMessage.value = response.errorBody()?.string()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _errorMessage.value = t.message
            }
        })
    }
}