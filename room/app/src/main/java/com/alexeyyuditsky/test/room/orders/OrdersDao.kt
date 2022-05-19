package com.alexeyyuditsky.test.room.orders

import androidx.room.*

@Dao
interface OrdersDao {

    @Transaction
    @Query("SELECT * FROM orders")
    fun getAllOrders(): List<OrderDbEntity>

    @Insert(entity = OrderDbEntity::class)
    fun createOrder(orderDbEntity: OrderDbEntity)

    @Delete(entity = OrderDbEntity::class)
    fun deleteOrder(orderDbEntity: OrderWithIdTuple)

}