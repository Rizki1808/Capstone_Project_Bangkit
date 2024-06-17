package com.example.capstoneproject.data.database

import android.app.Application
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class DrugsRepository(application: Application) {
    private val mDrugsDao: DrugsDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = DrugsRoomDatabase.getDatabase(application)
        mDrugsDao = db.drugsDao()
    }

    fun getAllDrugs() = mDrugsDao.getAllDrugs()

    fun getDrugById(drugId: Int) = mDrugsDao.getDrugById(drugId)

    fun insertDrug(drug: Drugs) {
        executorService.execute {
            mDrugsDao.insertDrug(drug)
        }
    }

    fun updateDrug(drug: Drugs) {
        executorService.execute {
            mDrugsDao.updateDrug(drug)
        }
    }

    fun deleteDrug(drug: Drugs) {
        executorService.execute {
            mDrugsDao.deleteDrug(drug)
        }
    }

    fun deleteAllDrugs() {
        executorService.execute {
            mDrugsDao.deleteAllDrugs()
        }
    }
}
