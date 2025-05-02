package com.example.databases.Model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "estudiantes")
data class EstudianteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val carrera: String
)