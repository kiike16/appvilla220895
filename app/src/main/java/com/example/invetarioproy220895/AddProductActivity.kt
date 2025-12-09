package com.example.invetarioproy220895

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etPrecio = findViewById<EditText>(R.id.etPrecio)
        val etCantidad = findViewById<EditText>(R.id.etCantidad)
        val etCategoria = findViewById<EditText>(R.id.etCategoria)
        val etCodigo = findViewById<EditText>(R.id.etCodigo)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        val dbHelper = DBHelper(this)

        btnGuardar.setOnClickListener {

            if (etNombre.text.isEmpty() || etPrecio.text.isEmpty() || etCantidad.text.isEmpty()) {
                Toast.makeText(this, "Llena todos los campos obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nombre = etNombre.text.toString()
            val precio = etPrecio.text.toString().toDouble()
            val cantidad = etCantidad.text.toString().toInt()
            val categoria = etCategoria.text.toString()
            val codigo = etCodigo.text.toString()

            val resultado = dbHelper.insertarProducto(
                nombre,
                precio,
                cantidad,
                categoria,
                codigo
            )

            if (resultado) {
                Toast.makeText(this, "Producto guardado ✅", Toast.LENGTH_SHORT).show()
                limpiarCampos()
            } else {
                Toast.makeText(this, "Error al guardar ❌", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun limpiarCampos() {
        findViewById<EditText>(R.id.etNombre).setText("")
        findViewById<EditText>(R.id.etPrecio).setText("")
        findViewById<EditText>(R.id.etCantidad).setText("")
        findViewById<EditText>(R.id.etCategoria).setText("")
        findViewById<EditText>(R.id.etCodigo).setText("")
    }
}

