package com.alexeyyuditsky.room.model.boxes.room.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.alexeyyuditsky.room.model.accounts.room.entities.AccountDbEntity

data class SettingWithEntitiesTuple(
    @Embedded val accountBoxSettingDbEntity: AccountBoxSettingDbEntity,

    @Relation(
        parentColumn = "account_id",
        entityColumn = "id"
    )
    val accountDbEntity: AccountDbEntity,

    @Relation(
        parentColumn = "box_id",
        entityColumn = "id"
    )
    val boxDbEntity: BoxDbEntity
)