package com.example.databases

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.databases.Model.EstudianteEntity
import com.example.databases.Model.databaseApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EstudianteAdapter(
    private var estudiantes: List<EstudianteEntity>,
    private val context: Context
) : RecyclerView.Adapter<EstudianteAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombre_completo)
        val tvApellido: TextView = itemView.findViewById(R.id.tvEdad)
        val tvCarrera: TextView = itemView.findViewById(R.id.tvCarrera)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_estudiante, parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val estudiante = estudiantes[position]
        holder.tvNombre.text = estudiante.nombre
        holder.tvApellido.text = estudiante.Edad.toString()
        holder.tvCarrera.text = estudiante.carrera

        holder.itemView.setOnLongClickListener {
            val popup = PopupMenu(context, holder.itemView)
            popup.menu.add("Editar")
            popup.menu.add("Eliminar")

            popup.setOnMenuItemClickListener { item ->
                when (item.title) {
                    "Editar" -> {
                        mostrarDialogoEditar(estudiante)
                        true
                    }
                    "Eliminar" -> {
                        eliminarEstudiante(estudiante)
                        true
                    }
                    else -> false
                }
            }

            popup.show()
            true
        }
    }

    override fun getItemCount(): Int = estudiantes.size

    private fun mostrarDialogoEditar(estudiante: EstudianteEntity) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialogo_editar_estudiante, null)
        val etNombre = dialogView.findViewById<EditText>(R.id.etEditarNombre)
        val etEdad = dialogView.findViewById<EditText>(R.id.etEditarEdad)
        val etCarrera = dialogView.findViewById<EditText>(R.id.etEditarCarrera)

        etNombre.setText(estudiante.nombre)
        etEdad.setText(estudiante.Edad.toString())
        etCarrera.setText(estudiante.carrera)

        AlertDialog.Builder(context)
            .setTitle("Editar estudiante")
            .setView(dialogView)
            .setPositiveButton("Guardar") { _, _ ->
                val actualizado = estudiante.copy(
                    nombre = etNombre.text.toString(),
                    Edad = etEdad.text.toString().toIntOrNull() ?: 0,
                    carrera = etCarrera.text.toString()
                )
                CoroutineScope(Dispatchers.IO).launch {
                    databaseApplication.database.estudianteDao().actualizar(actualizado)
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun eliminarEstudiante(estudiante: EstudianteEntity) {
        AlertDialog.Builder(context)
            .setTitle("Eliminar")
            .setMessage("¿Seguro que quieres eliminar a ${estudiante.nombre}?")
            .setPositiveButton("Sí") { _, _ ->
                CoroutineScope(Dispatchers.IO).launch {
                    databaseApplication.database.estudianteDao().eliminar(estudiante)
                }
            }
            .setNegativeButton("No", null)
            .show()
    }
}
