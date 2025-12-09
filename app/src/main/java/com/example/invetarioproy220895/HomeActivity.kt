package com.example.invetarioproy220895

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val btnAgregar = findViewById<Button>(R.id.btnAgregarProducto)
        val btnVerProductos = findViewById<Button>(R.id.btnVerProductos)

        // Botón: Agregar Producto
        btnAgregar.setOnClickListener {
            val intent = Intent(this, AddProductActivity::class.java)
            startActivity(intent)
        }

        // Botón: Ver Inventario
        btnVerProductos.setOnClickListener {
            val intent = Intent(this, ListProductsActivity::class.java)
            startActivity(intent)
        }
    }
}
