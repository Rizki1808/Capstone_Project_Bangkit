package com.example.capstoneproject.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi

@Database(entities = [Drugs::class], version = 1, exportSchema = false)
abstract class DrugsRoomDatabase : RoomDatabase() {
    abstract fun drugsDao(): DrugsDao

    companion object {
        @Volatile
        private var INSTANCE: DrugsRoomDatabase? = null

        @OptIn(InternalCoroutinesApi::class)
        @JvmStatic
        fun getDatabase(context: Context): DrugsRoomDatabase {
            if (INSTANCE == null) {
                synchronized(DrugsRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        DrugsRoomDatabase::class.java, "drugs_database"
                    )
                        .build()
                }
            }
            return INSTANCE as DrugsRoomDatabase
        }
    }
}