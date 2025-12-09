package com.example.invetarioproy220895

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {

    companion object {
        const val DATABASE_NAME = "Inventario.db"
        const val DATABASE_VERSION = 1

        const val TABLE_PRODUCTOS = "productos"
        const val COLUMN_ID = "id"
        const val COLUMN_NOMBRE = "nombre"
        const val COLUMN_PRECIO = "precio"
        const val COLUMN_CANTIDAD = "cantidad"
        const val COLUMN_CATEGORIA = "categoria"
        const val COLUMN_CODIGO = "codigo_barras"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_PRODUCTOS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NOMBRE TEXT,
                $COLUMN_PRECIO REAL,
                $COLUMN_CANTIDAD INTEGER,
                $COLUMN_CATEGORIA TEXT,
                $COLUMN_CODIGO TEXT
            )
        """.trimIndent()

        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PRODUCTOS")
        onCreate(db)
    }

    // ✅ Función para insertar productos
    fun insertarProducto(
        nombre: String,
        precio: Double,
        cantidad: Int,
        categoria: String,
        codigoBarras: String
    ): Boolean {

        val db = this.writableDatabase
        val valores = ContentValues()

        valores.put(COLUMN_NOMBRE, nombre)
        valores.put(COLUMN_PRECIO, precio)
        valores.put(COLUMN_CANTIDAD, cantidad)
        valores.put(COLUMN_CATEGORIA, categoria)
        valores.put(COLUMN_CODIGO, codigoBarras)

        val resultado = db.insert(TABLE_PRODUCTOS, null, valores)

        return resultado != -1L
    }
    fun obtenerProductos(): List<Producto> {
        val lista = mutableListOf<Producto>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_PRODUCTOS", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE))
                val categoria = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORIA))
                val cantidad = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CANTIDAD))
                val precio = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRECIO))

                lista.add(
                    Producto(
                        id = id,
                        nombre = nombre,
                        categoria = categoria,
                        cantidad = cantidad,
                        precio = precio
                    )
                )
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return lista
    }

}
