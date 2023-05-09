package com.alexeyyuditsky.test.room.orders

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Relation
import com.alexeyyuditsky.test.room.customers.CustomerDbEntity
import com.alexeyyuditsky.test.room.products.ProductDbEntity

data class OrderWithIdTuple(
    @ColumnInfo(name = "id") val productId: Long
)

data class OrderUpdateDateTuple(
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "created_at") val createdAt: String
)

data class OrderTuple(
    @ColumnInfo(name = "customer_id") val customerId: Long,
    @ColumnInfo(name = "product_id") val productId: Long,
    val name: String,
)

data class CustomersAndProductsAndOrdersTuple(
    @Embedded val orderTuple: OrderTuple,

    @Relation(
        parentColumn = "customer_id",
        entityColumn = "id"
    )
    val customerDbEntity: CustomerDbEntity,

    @Relation(
        parentColumn = "product_id",
        entityColumn = "id"
    )
    val productDbEntity: ProductDbEntity
) {

    override fun toString(): String {
        return "orderTuple.customerId = ${orderTuple.customerId}\n" +
                "orderTuple.productId = ${orderTuple.productId}\n" +
                "orderTuple.name = ${orderTuple.name}\n\n" +
                "customerDbEntity.id = ${customerDbEntity.id}\n" +
                "customerDbEntity.name = ${customerDbEntity.name}\n\n" +
                "productDbEntity.id = ${productDbEntity.id}\n" +
                "productDbEntity.company = ${productDbEntity.company}\n" +
                "productDbEntity.name = ${productDbEntity.name}\n" +
                "productDbEntity.itemsCount = ${productDbEntity.itemsCount}\n" +
                "productDbEntity.price = ${productDbEntity.price}"
    }

}