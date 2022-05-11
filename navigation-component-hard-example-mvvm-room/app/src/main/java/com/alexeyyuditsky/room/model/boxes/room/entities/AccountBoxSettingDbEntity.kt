package com.alexeyyuditsky.room.model.boxes.room.entities

import androidx.room.*
import com.alexeyyuditsky.room.model.accounts.room.entities.AccountDbEntity

@Entity(
    tableName = "accounts_boxes_settings",
    primaryKeys = ["account_id", "box_id"],
    indices = [
        Index("box_id")
    ],
    foreignKeys = [
        ForeignKey(
            entity = AccountDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["account_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = BoxDbEntity::class,
            parentColumns = ["id"],
            childColumns = ["box_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        )
    ]
)
data class AccountBoxSettingDbEntity(
    @ColumnInfo(name = "account_id") val accountId: Long,
    @ColumnInfo(name = "box_id") val boxId: Long,
    @ColumnInfo(name = "is_active") val isActive: Boolean,
)
