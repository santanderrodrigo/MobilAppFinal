package com.example.fianalapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "AstronomyClub.db"
        const val DATABASE_VERSION = 1

        const val TABLE_NAME = "Socios"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "nombre"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PHONE = "telefono"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_EMAIL TEXT NOT NULL,
                $COLUMN_PHONE TEXT NOT NULL
            )
        """.trimIndent()
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addSocio(nombre: String, email: String, telefono: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, nombre)
            put(COLUMN_EMAIL, email)
            put(COLUMN_PHONE, telefono)
        }
        return db.insert(TABLE_NAME, null, values)
    }

    fun getAllSocios(): List<Map<String, String>> {
        val socios = mutableListOf<Map<String, String>>()
        val db = readableDatabase
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, null)

        with(cursor) {
            while (moveToNext()) {
                val socio = mapOf(
                    COLUMN_ID to getString(getColumnIndexOrThrow(COLUMN_ID)),
                    COLUMN_NAME to getString(getColumnIndexOrThrow(COLUMN_NAME)),
                    COLUMN_EMAIL to getString(getColumnIndexOrThrow(COLUMN_EMAIL)),
                    COLUMN_PHONE to getString(getColumnIndexOrThrow(COLUMN_PHONE))
                )
                socios.add(socio)
            }
            close()
        }
        return socios
    }

    fun updateSocio(id: Int, nombre: String, email: String, telefono: String): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, nombre)
            put(COLUMN_EMAIL, email)
            put(COLUMN_PHONE, telefono)
        }
        return db.update(TABLE_NAME, values, "$COLUMN_ID=?", arrayOf(id.toString()))
    }

    fun deleteSocio(id: Int): Int {
        val db = writableDatabase
        return db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(id.toString()))
    }
}