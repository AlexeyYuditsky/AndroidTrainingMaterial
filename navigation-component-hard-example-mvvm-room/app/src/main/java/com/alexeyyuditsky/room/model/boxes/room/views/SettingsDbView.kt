package com.alexeyyuditsky.room.model.boxes.room.views

import androidx.room.ColumnInfo
import androidx.room.DatabaseView

@DatabaseView(
    viewName = "settings_view",
    value = "SELECT" +
            "   accounts.id AS account_id," +
            "   boxes.id AS box_id," +
            "   boxes.color_name," +
            "   boxes.color_value," +
            "   ifnull(accounts_boxes_settings.is_active, 1) AS is_active " +
            "FROM accounts, boxes " +
            "LEFT JOIN accounts_boxes_settings" +
            "   ON accounts_boxes_settings.account_id = accounts.id" +
            "       AND accounts_boxes_settings.box_id = boxes.id"
)
data class SettingsDbView(
    @ColumnInfo(name = "account_id") val accountId: Long,
    @ColumnInfo(name = "box_id") val boxId: Long,
    @ColumnInfo(name = "color_name") val colorName: String,
    @ColumnInfo(name = "color_value") val colorValue: String,
    @ColumnInfo(name = "is_active") val isActive: Boolean,
)