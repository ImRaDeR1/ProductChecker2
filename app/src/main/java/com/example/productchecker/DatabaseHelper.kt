package com.example.productchecker

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import kotlin.random.Random

data class Product(
    val id: Int,
    val name: String,
    val expirationDate: String,
    val receptionDate: String,  // Новое поле
    val supplier: String,
    val city: String,
    val country: String,
    val companyName: String,
    val calories: Int,
    val barcode: String  // Новое поле
)

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "products.db"
        private const val DATABASE_VERSION = 2
        private const val TABLE_NAME = "products"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_EXPIRATION_DATE = "expiration_date"
        private const val COLUMN_SUPPLIER = "supplier"
        private const val COLUMN_CITY = "city"
        private const val COLUMN_COUNTRY = "country"
        private const val COLUMN_COMPANY_NAME = "company_name"
        private const val COLUMN_CALORIES = "calories"
        private const val COLUMN_RECEPTION_DATE = "reception_date"
        private const val COLUMN_BARCODE = "barcode"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
    CREATE TABLE $TABLE_NAME (
        $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
        $COLUMN_NAME TEXT,
        $COLUMN_EXPIRATION_DATE TEXT,
        $COLUMN_RECEPTION_DATE TEXT,
        $COLUMN_SUPPLIER TEXT,
        $COLUMN_CITY TEXT,
        $COLUMN_COUNTRY TEXT,
        $COLUMN_COMPANY_NAME TEXT,
        $COLUMN_CALORIES INTEGER,
        $COLUMN_BARCODE TEXT
    )
    """.trimIndent()
        db.execSQL(createTable)
    }


    fun addProduct(
        name: String,
        expirationDate: String,
        receptionDate: String,  // Новое поле
        supplier: String,
        city: String,
        country: String,
        companyName: String,
        calories: Int,
        barcode: String  // Новое поле
    ) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_EXPIRATION_DATE, expirationDate)
            put(COLUMN_RECEPTION_DATE, receptionDate)  // Новое поле
            put(COLUMN_SUPPLIER, supplier)
            put(COLUMN_CITY, city)
            put(COLUMN_COUNTRY, country)
            put(COLUMN_COMPANY_NAME, companyName)
            put(COLUMN_CALORIES, calories)
            put(COLUMN_BARCODE, barcode)  // Новое поле
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun deleteProduct(id: Int) {
        val db = writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(id.toString()))
        db.close()
    }

    fun updateProduct(
        id: Int,
        name: String,
        expirationDate: String,
        receptionDate: String,  // Новое поле
        supplier: String,
        city: String,
        country: String,
        companyName: String,
        calories: Int,
        barcode: String  // Новое поле
    ) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_EXPIRATION_DATE, expirationDate)
            put(COLUMN_RECEPTION_DATE, receptionDate)  // Новое поле
            put(COLUMN_SUPPLIER, supplier)
            put(COLUMN_CITY, city)
            put(COLUMN_COUNTRY, country)
            put(COLUMN_COMPANY_NAME, companyName)
            put(COLUMN_CALORIES, calories)
            put(COLUMN_BARCODE, barcode)  // Новое поле
        }
        db.update(TABLE_NAME, values, "$COLUMN_ID=?", arrayOf(id.toString()))
        db.close()
    }

    fun clearDatabase() {
        val db = writableDatabase
        db.execSQL("DELETE FROM $TABLE_NAME")
        db.close()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun fillDatabaseWithRandomData(numberOfProducts: Int=100) {
        val db = writableDatabase
        val names = listOf(
            "Молоко", "Хлеб", "Яблоки", "Бананы", "Апельсины",
            "Груши", "Киви", "Виноград", "Морковь", "Картофель",
            "Помидоры", "Огурцы", "Перец", "Лук", "Чеснок"
        )

        val suppliers = listOf(
            "Поставщик 1", "Поставщик 2", "Поставщик 3", "Поставщик 4", "Поставщик 5",
            "Поставщик 6", "Поставщик 7", "Поставщик 8", "Поставщик 9", "Поставщик 10",
            "Поставщик 11", "Поставщик 12", "Поставщик 13", "Поставщик 14", "Поставщик 15"
        )

        val cities = listOf(
            "Москва", "Санкт-Петербург", "Казань", "Новосибирск", "Екатеринбург",
            "Нижний Новгород", "Ростов-на-Дону", "Калуга", "Краснодар", "Воронеж",
            "Челябинск", "Самара", "Уфа", "Пенза", "Тюмень"
        )

        val countries = listOf(
            "Россия", "Беларусь", "Казахстан", "Украина", "Грузия",
            "Армения", "Латвия", "Литва", "Эстония", "Молдавия",
            "Азербайджан", "Таджикистан", "Узбекистан", "Киргизия", "Туркменистан"
        )

        val companyNames = listOf(
            "Компания 1", "Компания 2", "Компания 3", "Компания 4", "Компания 5",
            "Компания 6", "Компания 7", "Компания 8", "Компания 9", "Компания 10",
            "Компания 11", "Компания 12", "Компания 13", "Компания 14", "Компания 15"
        )

        for (i in 0 until numberOfProducts) {
            val values = ContentValues().apply {
                put(COLUMN_NAME, names.random())
                put(COLUMN_EXPIRATION_DATE, "2024-01-${Random.nextInt(1, 30)}")
                put(COLUMN_RECEPTION_DATE, "2023-12-${Random.nextInt(1, 30)}") // Случайная дата поступления
                put(COLUMN_SUPPLIER, suppliers.random())
                put(COLUMN_CITY, cities.random())
                put(COLUMN_COUNTRY, countries.random())
                put(COLUMN_COMPANY_NAME, companyNames.random())
                put(COLUMN_CALORIES, Random.nextInt(50, 300))
                put(COLUMN_BARCODE, "123456789${Random.nextInt(0, 10)}") // Случайный штрихкод
            }
            db.insert(TABLE_NAME, null, values)
        }
        db.close()
        Log.d("DatabaseHelper", "$numberOfProducts случайных продуктов добавлено в базу данных.")
    }

    fun getAllProducts(): List<Product> {
        val products = mutableListOf<Product>()
        val db = readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val expirationDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPIRATION_DATE))
                val receptionDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RECEPTION_DATE))  // Новое поле
                val supplier = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUPPLIER))
                val city = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CITY))
                val country = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COUNTRY))
                val companyName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COMPANY_NAME))
                val calories = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CALORIES))
                val barcode = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BARCODE))  // Новое поле

                products.add(Product(id, name, expirationDate, receptionDate, supplier, city, country, companyName, calories, barcode))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        Log.d("DatabaseHelper", "Всего продуктов: ${products.size}")
        return products
    }
}