package com.example.examen3

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)
        val usuario = intent.getStringExtra("usuario") ?: ""

        binding.tvBienvenida.text = "Bienvenido, $usuario"

        // Recuperar última conexión ANTES de actualizar
        val ultimaConexion = dbHelper.obtenerUltimaConexion(usuario)
        binding.tvUltimaConexion.text = "Última conexión: ${ultimaConexion ?: "Primera vez"}"

        // Actualizar con la fecha/hora actual
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        val fechaActual = sdf.format(Date())
        dbHelper.actualizarUltimaConexion(usuario, fechaActual)
    }
}