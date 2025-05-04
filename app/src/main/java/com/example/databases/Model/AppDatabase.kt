package com.example.databases.Model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [EstudianteEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase(){

    abstract fun estudianteDao():EstudianteDAO
}
