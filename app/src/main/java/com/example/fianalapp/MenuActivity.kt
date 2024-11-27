package com.example.fianalapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.widget.Button




class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val buttonSocios = findViewById<Button>(R.id.buttonSocios)
        buttonSocios.setOnClickListener {
            // Navegar a la pantalla de gestionar socios
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val buttonOtros = findViewById<Button>(R.id.buttonOtros)
        buttonOtros.setOnClickListener {
            // Mensaje de funcionalidad futura
            // Puedes agregar más actividades si es necesario
        }
    }
}