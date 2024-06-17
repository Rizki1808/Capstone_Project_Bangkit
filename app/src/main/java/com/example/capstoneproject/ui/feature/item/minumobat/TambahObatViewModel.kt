package com.example.capstoneproject.ui.feature.item.minumobat

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.capstoneproject.data.database.Drugs
import com.example.capstoneproject.data.database.DrugsRepository

class TambahObatViewModel(application: Application) : AndroidViewModel(application) {

    private val mDrugsRepository: DrugsRepository = DrugsRepository(application)

    fun insertDrug(drug: Drugs) {
        mDrugsRepository.insertDrug(drug)
    }

    fun updateDrug(drug: Drugs) {
        mDrugsRepository.updateDrug(drug)
    }

    fun deleteDrug(drug: Drugs) {
        mDrugsRepository.deleteDrug(drug)
    }
}
