package com.example.capstoneproject.data.tools

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.capstoneproject.data.di.Injection
import com.example.capstoneproject.ui.feature.item.infopenyakit.InfoPenyakitViewModel
import com.example.capstoneproject.ui.login.signin.SignInViewModel
import com.example.capstoneproject.ui.login.signup.SignUpViewModel
import com.example.capstoneproject.ui.main.MainViewModel
import com.example.capstoneproject.ui.profile.ProfileViewModel

class ViewModelFactory(private val repository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SignInViewModel::class.java) -> {
                SignInViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> {
                SignUpViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(repository) as T
            }
            modelClass.isAssignableFrom(InfoPenyakitViewModel::class.java) -> {
                InfoPenyakitViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ViewModelFactory(Injection.provideRepository(context)).also {
                    INSTANCE = it
                }
            }
        }
    }
}