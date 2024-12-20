package com.example.productchecker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(
    private var products: List<Product>,
    private val onProductSelected: (Int) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productName: TextView = itemView.findViewById(R.id.productName)
        private val expirationDate: TextView = itemView.findViewById(R.id.expirationDate)
        private val supplier: TextView = itemView.findViewById(R.id.supplier)
        private val city: TextView = itemView.findViewById(R.id.city)
        private val country: TextView = itemView.findViewById(R.id.country)
        private val companyName: TextView = itemView.findViewById(R.id.companyName)
        private val calories: TextView = itemView.findViewById(R.id.calories)
        private val receptionDate: TextView = itemView.findViewById(R.id.receptionDate)
        private val barcode: TextView = itemView.findViewById(R.id.barcode)

        fun bind(product: Product, isSelected: Boolean, onProductSelected: (Int) -> Unit) {
            productName.text = product.name
            expirationDate.text = product.expirationDate
            receptionDate.text = product.receptionDate  // Новое поле
            supplier.text = product.supplier
            city.text = product.city
            country.text = product.country
            companyName.text = product.companyName
            calories.text = product.calories.toString()
            barcode.text = product.barcode  // Новое поле

            // Устанавливаем обработчик клика для выбора продукта
            itemView.setOnClickListener {
                onProductSelected(product.id)
            }

            // Изменяем фон в зависимости от выделения
            itemView.setBackgroundColor(
                if (isSelected) {
                    0xFFE0E0E0.toInt()  // Цвет для выделения
                } else {
                    0xFFb5b5d5.toInt()  // Цвет по умолчанию
                }
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position], position == selectedPosition) { productId ->
            selectedPosition = position
            onProductSelected(productId)
            notifyDataSetChanged()  // Обновляем весь список
        }
    }

    override fun getItemCount(): Int = products.size

    fun updateProducts(newProducts: List<Product>) {
        products = newProducts
        notifyDataSetChanged()
    }
}