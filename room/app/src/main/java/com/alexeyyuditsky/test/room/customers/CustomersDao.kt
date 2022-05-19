package com.alexeyyuditsky.test.room.customers

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface CustomersDao {

    @Transaction
    @Query("SELECT * FROM customers")
    suspend fun getAllCustomers(): List<CustomerDbEntity>

}