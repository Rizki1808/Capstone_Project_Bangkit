package com.example.capstoneproject.data.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.capstoneproject.data.database.Drugs

class DrugsDiffCallBack(
    private val oldDrugsList: List<Drugs>,
    private val newDrugsList: List<Drugs>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldDrugsList.size
    override fun getNewListSize(): Int = newDrugsList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldDrugsList[oldItemPosition].id == newDrugsList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldDrugs = oldDrugsList[oldItemPosition]
        val newDrugs = newDrugsList[newItemPosition]
        return oldDrugs.name == newDrugs.name && oldDrugs.dose == newDrugs.dose && oldDrugs.timeToTake == newDrugs.timeToTake && oldDrugs.instructions == newDrugs.instructions && oldDrugs.notes == newDrugs.notes
    }
}