package com.example.productchecker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.widget.Button

class DatabaseActivity : AppCompatActivity() {
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private var selectedProductId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database)

        databaseHelper = DatabaseHelper(this)
        recyclerView = findViewById(R.id.recyclerView)

        // Инициализация данных
        initializeProducts()

        // Получение всех продуктов и установка адаптера
        val products = databaseHelper.getAllProducts()
        productAdapter = ProductAdapter(products, ::onProductSelected)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = productAdapter

        // Обработка нажатия кнопки удаления
        findViewById<Button>(R.id.deleteProductButton).setOnClickListener {
            selectedProductId?.let {
                databaseHelper.deleteProduct(it)
                refreshProductList()
            }
        }

        // Обработка нажатия кнопки "Назад"
        findViewById<Button>(R.id.backButton).setOnClickListener {
            finish()  // Завершает текущую активность и возвращает на MainActivity
        }
    }

    private fun initializeProducts() {
        val products = databaseHelper.getAllProducts()
        if (products.isEmpty()) {
            databaseHelper.fillDatabaseWithRandomData(100)
            Log.d("DatabaseActivity", "Случайные данные успешно добавлены в базу данных.")
        } else {
            Log.d("DatabaseActivity", "Данные уже существуют в базе данных.")
        }
    }

    private fun refreshProductList() {
        val products = databaseHelper.getAllProducts()
        productAdapter.updateProducts(products)
    }

    private fun onProductSelected(productId: Int) {
        selectedProductId = productId
    }
}