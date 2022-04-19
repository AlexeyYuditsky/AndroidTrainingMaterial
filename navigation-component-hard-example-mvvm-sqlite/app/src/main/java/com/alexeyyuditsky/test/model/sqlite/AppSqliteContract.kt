package com.alexeyyuditsky.test.model.sqlite

object AccountsTable {
    const val TABLE_NAME = "accounts"
    const val COLUMN_ID = "id"
    const val COLUMN_EMAIL = "email"
    const val COLUMN_USERNAME = "username"
    const val COLUMN_PASSWORD = "password"
    const val COLUMN_CREATED_AT = "created_at"
}

object BoxesTable {
    const val TABLE_NAME = "boxes"
    const val COLUMN_ID = "id"
    const val COLUMN_COLOR_NAME = "color_name"
    const val COLUMN_COLOR_VALUE = "color_value"
}

object AccountsBoxesSettingsTable {
    const val TABLE_NAME = "accounts_boxes_settings"
    const val COLUMN_ACCOUNT_ID = "account_id"
    const val COLUMN_BOX_ID = "box_id"
    const val COLUMN_IS_ACTIVE = "is_active"
}
