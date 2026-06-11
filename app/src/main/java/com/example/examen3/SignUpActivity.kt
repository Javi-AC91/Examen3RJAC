package com.example.examen3

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        dbHelper = DBHelper(this)

        val etUsuario = findViewById<EditText>(R.id.etNuevoUsuario)
        val etContrasena = findViewById<EditText>(R.id.etNuevaContrasena)
        val btnRegistrarse = findViewById<Button>(R.id.btnRegistrarse)

        binding.btnRegistrarse.setOnClickListener {
            val usuario = binding.etNuevoUsuario.text.toString().trim()
            val contrasena = binding.etNuevaContrasena.text.toString().trim()

            if (usuario.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (dbHelper.existeUsuario(usuario)) {
                Toast.makeText(this, "El usuario ya está registrado", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val exito = dbHelper.registrarUsuario(usuario, contrasena)
            if (exito) {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show()
            }
        }
    }
}