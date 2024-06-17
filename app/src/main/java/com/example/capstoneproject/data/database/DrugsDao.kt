package com.example.capstoneproject.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DrugsDao {

    @Insert
    fun insertDrug(drug: Drugs)

    @Update
    fun updateDrug(drug: Drugs)

    @Delete
    fun deleteDrug(drug: Drugs)

    @Query("SELECT * FROM drugs")
    fun getAllDrugs(): LiveData<List<Drugs>>

    @Query("SELECT * FROM drugs WHERE id = :drugId")
    fun getDrugById(drugId: Int): Drugs?

    @Query("DELETE FROM drugs")
    fun deleteAllDrugs()
}