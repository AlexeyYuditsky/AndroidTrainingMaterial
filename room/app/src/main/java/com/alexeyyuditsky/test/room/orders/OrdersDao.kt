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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun changeOrder(orderDbEntity: OrderDbEntity)

    @Update(entity = OrderDbEntity::class)
    fun updateOrderDate(orderUpdateDateTuple: OrderUpdateDateTuple)

    @Transaction
    @Query(
        "select " +
                "customers.id as customer_id, " +
                "products.id as product_id, " +
                "products.name as name, " +
                "products.price as price " +
                "from customers, products " +
                "join orders on orders.customer_id = customers.id and orders.product_id = products.id " +
                "where customers.id = :customerId"
    )
    fun getCustomersAndProductsAndOrders(customerId: Long): CustomersAndProductsAndOrdersTuple

}

