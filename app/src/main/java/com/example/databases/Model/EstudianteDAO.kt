package com.example.databases.Model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface EstudianteDAO {
    @Insert
    suspend fun insertar(estudiante: EstudianteEntity)

    @Query("SELECT * FROM estudiantes")
    suspend fun obtenerTodos(): List<EstudianteEntity>

    @Update
    suspend fun actualizar(estudiante: EstudianteEntity)

    @Delete
    suspend fun eliminar(estudiante: EstudianteEntity)

}