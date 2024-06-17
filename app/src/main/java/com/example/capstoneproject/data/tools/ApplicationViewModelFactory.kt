package com.example.capstoneproject.data.tools

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.capstoneproject.ui.feature.item.minumobat.MinumObatViewModel
import com.example.capstoneproject.ui.feature.item.minumobat.TambahObatViewModel

class ApplicationViewModelFactory private constructor(private val mApplication: Application) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var INSTANCE: ApplicationViewModelFactory? = null
        @JvmStatic
        fun getInstance(application: Application): ApplicationViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ApplicationViewModelFactory::class.java) {
                    INSTANCE = ApplicationViewModelFactory(application)
                }
            }
            return INSTANCE as ApplicationViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MinumObatViewModel::class.java)) {
            return MinumObatViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(TambahObatViewModel::class.java)) {
            return TambahObatViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}