package com.example.databases
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.databases.Model.EstudianteEntity
import com.example.databases.Model.databaseApplication
import kotlinx.coroutines.launch
class MainActivity : AppCompatActivity() {
    private lateinit var recycler: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        var nombreEditText = findViewById<EditText>(R.id.nombreEditText)
        var carreraEditText = findViewById<EditText>(R.id.carreraEditText)
        var EdadEditText = findViewById<EditText>(R.id.EdadEditText)
        var agregarButton = findViewById<Button>(R.id.agregarButton)
        var ListarButton = findViewById<Button>(R.id.ListarButton)
        recycler = findViewById(R.id.recyclerEstudiantes)
        recycler.layoutManager = LinearLayoutManager(this)
        agregarButton.setOnClickListener {
            crearEstudiante(
                nombreEditText.text.toString(),
                EdadEditText.text.toString().toIntOrNull() ?: 0,
                carreraEditText.text.toString()
            )
            // Limpiar los campos de entrada
            nombreEditText.text.clear()
            carreraEditText.text.clear()
            EdadEditText.text.clear()
            // Mostrar la lista actualizada
        }
        ListarButton.setOnClickListener{
            Log.d("BOTON_LISTAR", "Se presionó el botón de listar")
            mostrarEstudiantes()
        }
    }
    fun crearEstudiante(nombre: String = "",Edad:Int = 0, carrera: String = "") {
        lifecycleScope.launch {
            databaseApplication.database.estudianteDao()
                .insertar(EstudianteEntity(nombre = nombre, Edad = Edad, carrera = carrera))
        }
    }
     fun mostrarEstudiantes() {
        lifecycleScope.launch {
            val estudiantes = databaseApplication.database.estudianteDao().obtenerTodos()
            runOnUiThread {
                recycler.adapter = EstudianteAdapter(estudiantes, this@MainActivity)

            }

        }
    }

}


