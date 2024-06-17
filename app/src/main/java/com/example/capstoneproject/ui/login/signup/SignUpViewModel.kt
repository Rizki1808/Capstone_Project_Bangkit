package com.example.capstoneproject.ui.login.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.capstoneproject.data.api.ApiConfig
import com.example.capstoneproject.data.response.UserResponse
import com.example.capstoneproject.data.tools.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel(private val repository: UserRepository) : ViewModel() {

    companion object {
        private const val TAG = "SignUpViewModel"
    }

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _dateOfBirth = MutableLiveData<String>()
    val dateofbirth: LiveData<String> = _dateOfBirth

    private val _gender = MutableLiveData<String>()
    val gender: LiveData<String> = _gender

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _successMessage = MutableLiveData<String>()
    val successMessage: LiveData<String> = _successMessage

    fun register(email: String, password: String, name: String, dateOfBirth: String, gender: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().register(email, password, name, dateOfBirth, gender)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val userResponse = response.body()
                    if (userResponse != null) {
                        if (userResponse.error) {
                            _errorMessage.value = userResponse.message
                        } else {
                            val loginResult = userResponse.loginResult
                            _name.value = loginResult?.name
                            _email.value = email
                            _password.value = password
                            _dateOfBirth.value = dateOfBirth
                            _gender.value = gender
                            _successMessage.value = userResponse.message
                        }
                    }
                } else {
                    _errorMessage.value = response.errorBody()?.string()
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = t.message
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}