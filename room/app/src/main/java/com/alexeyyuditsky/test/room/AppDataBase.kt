package com.alexeyyuditsky.test.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alexeyyuditsky.test.room.customers.CustomerDbEntity
import com.alexeyyuditsky.test.room.customers.CustomersDao
import com.alexeyyuditsky.test.room.orders.OrderDbEntity
import com.alexeyyuditsky.test.room.orders.OrdersDao
import com.alexeyyuditsky.test.room.products.ProductDbEntity
import com.alexeyyuditsky.test.room.products.ProductsDao

@Database(
    version = 1,
    entities = [
        CustomerDbEntity::class,
        ProductDbEntity::class,
        OrderDbEntity::class
    ]
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun getCustomersDao(): CustomersDao

    abstract fun getProductsDao(): ProductsDao

    abstract fun getOrdersDao(): OrdersDao

}