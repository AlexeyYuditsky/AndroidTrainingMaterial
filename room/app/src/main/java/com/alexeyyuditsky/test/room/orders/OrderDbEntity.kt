package com.alexeyyuditsky.test.room.orders

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.alexeyyuditsky.test.room.customers.CustomerDbEntity
import com.alexeyyuditsky.test.room.products.ProductDbEntity

@Entity(
    tableName = "orders",
    foreignKeys = [
        ForeignKey(
            entity = ProductDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["product_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CustomerDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["customer_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class OrderDbEntity(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "product_id") val productId: Long,
    @ColumnInfo(name = "customer_id") val customerId: Long,
    @ColumnInfo(name = "created_at") val createdAt: String,
    @ColumnInfo(name = "items_count") val itemsCount: Int,
    @ColumnInfo(name = "price") val price: Int
) {

    companion object {
        fun createOrder(text: String): OrderDbEntity {
            val data = text.split(',')
            return OrderDbEntity(
                id = 0, // SQLite generates identifier automatically if ID = 0
                productId = data[0].toLong(),
                customerId = data[1].toLong(),
                createdAt = data[2],
                itemsCount = data[3].toInt(),
                price = data[4].toInt()
            )
        }
    }

}