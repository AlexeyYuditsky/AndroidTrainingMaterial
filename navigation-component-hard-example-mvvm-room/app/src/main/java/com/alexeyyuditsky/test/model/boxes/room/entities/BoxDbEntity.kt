package com.alexeyyuditsky.test.model.boxes.room.entities

import android.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alexeyyuditsky.test.model.boxes.entities.Box

@Entity(tableName = "boxes")
data class BoxDbEntity(
    @ColumnInfo(name = "id") @PrimaryKey val id: Long,
    @ColumnInfo(name = "color_name") val colorName: String,
    @ColumnInfo(name = "color_value") val colorValue: String
) {

    fun toBox(): Box = Box(
        id = id,
        colorName = colorName,
        colorValue = Color.parseColor(colorValue)
    )

}