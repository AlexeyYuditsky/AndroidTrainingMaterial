package com.alexeyyuditsky.test.room.orders

import androidx.room.ColumnInfo

data class OrderWithIdTuple(
    @ColumnInfo(name = "id") val productId: Long
)