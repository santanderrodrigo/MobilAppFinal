package com.example.fianalapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var socioAdapter: SocioAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar la base de datos
        dbHelper = DatabaseHelper(this)

        // Configurar RecyclerView
        recyclerView = findViewById(R.id.recyclerViewSocios)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Cargar la lista de socios
        loadSocios()

        // Configurar botón flotante para agregar nuevos socios
        findViewById<FloatingActionButton>(R.id.buttonAddSocio).setOnClickListener {
            showAddSocioDialog()
        }
    }

    /**
     * Método para cargar la lista de socios en el RecyclerView
     */
    private fun loadSocios() {
        val socios = dbHelper.getAllSocios()
        socioAdapter = SocioAdapter(socios, ::onSocioLongClick)
        recyclerView.adapter = socioAdapter
    }

    /**
     * Mostrar un diálogo para agregar un nuevo socio
     */
    private fun showAddSocioDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_socio, null)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Agregar Socio")
            .setView(dialogView)
            .setPositiveButton("Guardar") { _, _ ->
                val nombre = dialogView.findViewById<android.widget.EditText>(R.id.editTextNombre).text.toString()
                val email = dialogView.findViewById<android.widget.EditText>(R.id.editTextEmail).text.toString()
                val telefono = dialogView.findViewById<android.widget.EditText>(R.id.editTextTelefono).text.toString()

                if (nombre.isNotEmpty() && email.isNotEmpty() && telefono.isNotEmpty()) {
                    dbHelper.addSocio(nombre, email, telefono)
                    loadSocios()
                    Toast.makeText(this, "Socio agregado exitosamente", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .create()
        dialog.show()
    }

    /**
     * Acción al hacer una pulsación larga sobre un socio
     */
    private fun onSocioLongClick(socioId: Int) {
        val options = arrayOf("Editar", "Eliminar")
        AlertDialog.Builder(this)
            .setTitle("Selecciona una opción")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> showEditSocioDialog(socioId) // Editar
                    1 -> deleteSocio(socioId)         // Eliminar
                }
            }
            .show()
    }

    /**
     * Mostrar un diálogo para editar un socio
     */
    private fun showEditSocioDialog(socioId: Int) {
        val socio = dbHelper.getAllSocios().find { it[DatabaseHelper.COLUMN_ID]?.toInt() == socioId }
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_socio, null).apply {
            findViewById<android.widget.EditText>(R.id.editTextNombre).setText(socio?.get(DatabaseHelper.COLUMN_NAME))
            findViewById<android.widget.EditText>(R.id.editTextEmail).setText(socio?.get(DatabaseHelper.COLUMN_EMAIL))
            findViewById<android.widget.EditText>(R.id.editTextTelefono).setText(socio?.get(DatabaseHelper.COLUMN_PHONE))
        }

        AlertDialog.Builder(this)
            .setTitle("Editar Socio")
            .setView(dialogView)
            .setPositiveButton("Actualizar") { _, _ ->
                val nombre = dialogView.findViewById<android.widget.EditText>(R.id.editTextNombre).text.toString()
                val email = dialogView.findViewById<android.widget.EditText>(R.id.editTextEmail).text.toString()
                val telefono = dialogView.findViewById<android.widget.EditText>(R.id.editTextTelefono).text.toString()

                if (nombre.isNotEmpty() && email.isNotEmpty() && telefono.isNotEmpty()) {
                    dbHelper.updateSocio(socioId, nombre, email, telefono)
                    loadSocios()
                    Toast.makeText(this, "Socio actualizado exitosamente", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .create()
            .show()
    }

    /**
     * Eliminar un socio
     */
    private fun deleteSocio(socioId: Int) {
        dbHelper.deleteSocio(socioId)
        loadSocios()
        Toast.makeText(this, "Socio eliminado", Toast.LENGTH_SHORT).show()
    }
}