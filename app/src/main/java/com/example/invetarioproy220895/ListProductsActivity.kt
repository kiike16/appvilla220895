package com.example.invetarioproy220895

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListProductsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_products)

        val rvProductos = findViewById<RecyclerView>(R.id.rvProductos)
        val etBuscar = findViewById<EditText>(R.id.etBuscar)
        val tvResumen = findViewById<TextView>(R.id.tvResumen)

        val dbHelper = DBHelper(this)

        // Obtener productos
        val productos = dbHelper.obtenerProductos()

        // Texto resumen
        tvResumen.text = "📦 ${productos.size} productos registrados"

        // Configurar RecyclerView
        rvProductos.layoutManager = LinearLayoutManager(this)
        val adapter = ProductoAdapter(productos)
        rvProductos.adapter = adapter

        // Búsqueda en tiempo real
        etBuscar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filtrar(s.toString())
            }
        })
    }
}
