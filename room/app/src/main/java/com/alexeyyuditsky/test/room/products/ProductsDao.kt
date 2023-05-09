package com.alexeyyuditsky.test.room.products

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface ProductsDao {

    @Transaction
    @Query("SELECT * FROM products")
    fun getAllProducts(): List<ProductDbEntity>

}