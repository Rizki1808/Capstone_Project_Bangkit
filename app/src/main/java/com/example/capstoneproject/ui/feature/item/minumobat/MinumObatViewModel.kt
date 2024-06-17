package com.example.capstoneproject.ui.feature.item.minumobat

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.capstoneproject.data.database.Drugs
import com.example.capstoneproject.data.database.DrugsRepository

class MinumObatViewModel(application: Application) : ViewModel() {

    private val mDrugsRepository: DrugsRepository = DrugsRepository(application)

    fun getAllDrugs(): LiveData<List<Drugs>> = mDrugsRepository.getAllDrugs()
}