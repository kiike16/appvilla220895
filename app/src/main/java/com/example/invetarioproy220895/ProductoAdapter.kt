package com.example.invetarioproy220895

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

class ProductoAdapter(private var lista: List<Producto>) :
    RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    private var listaOriginal: List<Producto> = lista.toList()

    inner class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombreProducto)
        val tvCategoria: TextView = itemView.findViewById(R.id.tvCategoriaProducto)
        val tvCantidad: TextView = itemView.findViewById(R.id.tvCantidadProducto)
        val tvPrecio: TextView = itemView.findViewById(R.id.tvPrecioProducto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = lista[position]
        holder.tvNombre.text = producto.nombre
        holder.tvCategoria.text = producto.categoria
        holder.tvCantidad.text = producto.cantidad.toString()
        holder.tvPrecio.text = "$${producto.precio}"
    }

    override fun getItemCount(): Int = lista.size

    fun filtrar(texto: String) {
        val query = texto.lowercase(Locale.getDefault())

        lista = if (query.isEmpty()) {
            listaOriginal
        } else {
            listaOriginal.filter {
                it.nombre.lowercase(Locale.getDefault()).contains(query) ||
                        it.categoria.lowercase(Locale.getDefault()).contains(query)
            }
        }
        notifyDataSetChanged()
    }
}
