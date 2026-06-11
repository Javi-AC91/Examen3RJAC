package com.example.examen3

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "usuarios.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE jugador (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                usuario TEXT UNIQUE,
                contrasena TEXT,
                ultima_conexion TEXT
            )
        """)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS jugador")
        onCreate(db)
    }

    fun registrarUsuario(usuario: String, contrasena: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("usuario", usuario)
            put("contrasena", contrasena)
        }
        val result = db.insert("jugador", null, values)
        db.close()
        return result != -1L
    }

    fun existeUsuario(usuario: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM jugador WHERE usuario = ?", arrayOf(usuario))
        val existe = cursor.count > 0
        cursor.close()
        db.close()
        return existe
    }

    fun validarCredenciales(usuario: String, contrasena: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM jugador WHERE usuario = ? AND contrasena = ?",
            arrayOf(usuario, contrasena)
        )
        val valido = cursor.count > 0
        cursor.close()
        db.close()
        return valido
    }

    fun actualizarUltimaConexion(usuario: String, fecha: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("ultima_conexion", fecha)
        }
        db.update("jugador", values, "usuario = ?", arrayOf(usuario))
        db.close()
    }

    fun obtenerUltimaConexion(usuario: String): String? {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT ultima_conexion FROM jugador WHERE usuario = ?", arrayOf(usuario))
        var fecha: String? = null
        if (cursor.moveToFirst()) {
            fecha = cursor.getString(0)
        }
        cursor.close()
        db.close()
        return fecha
    }
}