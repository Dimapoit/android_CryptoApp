package com.example.android_cryptoapp.database

import androidx.room.RoomDatabase

abstract class AppDatabase: RoomDatabase() {

    companion object {
        private var db: AppDatabase? = null
        private const val DB_NAME = "main.db"
        private val LOCK = Any()
    }


}