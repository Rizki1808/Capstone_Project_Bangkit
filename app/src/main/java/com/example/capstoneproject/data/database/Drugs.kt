package com.example.capstoneproject.data.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "drugs")
@Parcelize
data class Drugs(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "dose")
    var dose: String,

    @ColumnInfo(name = "time_to_take")
    var timeToTake: String,

    @ColumnInfo(name = "instructions")
    var instructions: String,

    @ColumnInfo(name = "notes")
    var notes: String?,

    @ColumnInfo(name = "date")
    var date: String? = null
) : Parcelable