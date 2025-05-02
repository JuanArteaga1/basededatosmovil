package com.example.databases

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.databases.Model.EstudianteEntity
import com.example.databases.Model.databaseApplication
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        var nombreEditText = findViewById<EditText>(R.id.nombreEditText)
        var carreraEditText = findViewById<EditText>(R.id.carreraEditText)
        var agregarButton = findViewById<Button>(R.id.agregarButton)


        agregarButton.setOnClickListener {
            crearEstudiante(
                nombreEditText.text.toString(),
                carreraEditText.text.toString()
            )
            // Limpiar los campos de entrada
            nombreEditText.text.clear()
            carreraEditText.text.clear()
            // Mostrar la lista actualizada
            mostrarEstudiantes()

        }
    }

    fun crearEstudiante(nombre: String = "", carrera: String = "") {
        lifecycleScope.launch {
            databaseApplication.database.estudianteDao()
                .insertar(EstudianteEntity(nombre = nombre, carrera = carrera))
        }
    }

    fun mostrarEstudiantes() {
        lifecycleScope.launch {
            // Obtener todos los estudiantes de la base de datos
            val listaEstudiantes = databaseApplication.database.estudianteDao().obtenerTodos()

            // Mostrar los estudiantes en la consola (Log)
            for (estudiante in listaEstudiantes) {
                Log.d("Estudiantes", "ID: ${estudiante.id}, Nombre: ${estudiante.nombre}, Carrera: ${estudiante.carrera}")
            }
        }
    }
}


