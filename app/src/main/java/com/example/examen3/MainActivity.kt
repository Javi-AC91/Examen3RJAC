package com.example.examen3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)

        binding.btnIniciarSesion.setOnClickListener {
            val usuario = binding.etUsuario.text.toString().trim()
            val contrasena = binding.etContrasena.text.toString().trim()

            if (dbHelper.validarCredenciales(usuario, contrasena)) {
                val intent = Intent(this, ProfileActivity::class.java)
                intent.putExtra("usuario", usuario)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Credenciales no son correctas", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnIrRegistro.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}