package com.example.databases.Model

import android.app.Application
import androidx.room.Room

class databaseApplication: Application() {
    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "mi_base_de_datos"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}
