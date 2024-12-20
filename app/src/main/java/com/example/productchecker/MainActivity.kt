package com.example.productchecker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var productIdEditText: EditText
    private lateinit var productNameEditText: EditText
    private lateinit var expirationDateEditText: EditText
    private lateinit var supplierEditText: EditText
    private lateinit var cityEditText: EditText
    private lateinit var countryEditText: EditText
    private lateinit var companyNameEditText: EditText
    private lateinit var caloriesEditText: EditText
    private lateinit var receptionDateEditText: EditText
    private lateinit var barcodeEditText: EditText
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        productIdEditText = findViewById(R.id.productId)
        productNameEditText = findViewById(R.id.productName)
        expirationDateEditText = findViewById(R.id.expirationDate)
        supplierEditText = findViewById(R.id.supplier)
        cityEditText = findViewById(R.id.city)
        countryEditText = findViewById(R.id.country)
        companyNameEditText = findViewById(R.id.companyName)
        caloriesEditText = findViewById(R.id.calories)
        receptionDateEditText = findViewById(R.id.receptionDate)
        barcodeEditText = findViewById(R.id.barcode)

        val openDatabaseButton: Button = findViewById(R.id.openDatabaseButton)
        val addProductButton: Button = findViewById(R.id.addProductButton)
        val deleteProductButton: Button = findViewById(R.id.deleteProductButton)
        val updateProductButton: Button = findViewById(R.id.updateProductButton)
        val clearDatabaseButton: Button = findViewById(R.id.clearDatabaseButton)

        databaseHelper = DatabaseHelper(this)

        openDatabaseButton.setOnClickListener {
            val intent = Intent(this, DatabaseActivity::class.java)
            startActivity(intent)
        }

        addProductButton.setOnClickListener {
            val name = productNameEditText.text.toString()
            val expiration = expirationDateEditText.text.toString()
            val reception = receptionDateEditText.text.toString()  // Новое поле
            val supplier = supplierEditText.text.toString()
            val city = cityEditText.text.toString()
            val country = countryEditText.text.toString()
            val companyName = companyNameEditText.text.toString()
            val calories = caloriesEditText.text.toString().toIntOrNull() ?: 0
            val barcode = barcodeEditText.text.toString()  // Новое поле

            databaseHelper.addProduct(name, expiration, reception, supplier, city, country, companyName, calories, barcode)
            clearInputFields()
        }

        deleteProductButton.setOnClickListener {
            val id = productIdEditText.text.toString().toIntOrNull()
            if (id != null) {
                databaseHelper.deleteProduct(id)
                productIdEditText.text.clear()
            }
        }

        updateProductButton.setOnClickListener {
            val id = productIdEditText.text.toString().toIntOrNull()
            val name = productNameEditText.text.toString()
            val expiration = expirationDateEditText.text.toString()
            val reception = receptionDateEditText.text.toString()  // Новое поле
            val supplier = supplierEditText.text.toString()
            val city = cityEditText.text.toString()
            val country = countryEditText.text.toString()
            val companyName = companyNameEditText.text.toString()
            val calories = caloriesEditText.text.toString().toIntOrNull() ?: 0
            val barcode = barcodeEditText.text.toString()  // Новое поле

            if (id != null) {
                databaseHelper.updateProduct(id, name, expiration, reception, supplier, city, country, companyName, calories, barcode)
                clearInputFields()
            }
        }

        clearDatabaseButton.setOnClickListener {
            databaseHelper.clearDatabase()
            Toast.makeText(this, "База данных очищена", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearInputFields() {
        productIdEditText.text.clear()
        productNameEditText.text.clear()
        expirationDateEditText.text.clear()
        supplierEditText.text.clear()
        cityEditText.text.clear()
        countryEditText.text.clear()
        companyNameEditText.text.clear()
        caloriesEditText.text.clear()
        receptionDateEditText.text.clear()  // Очистка нового поля
        barcodeEditText.text.clear()  // Очистка нового поля
    }
}