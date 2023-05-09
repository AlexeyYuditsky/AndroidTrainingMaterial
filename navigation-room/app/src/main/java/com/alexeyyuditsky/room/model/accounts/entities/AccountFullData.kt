package com.alexeyyuditsky.room.model.accounts.entities

import com.alexeyyuditsky.room.model.boxes.entities.BoxAndSettings

/**
 * Account info with all boxes and their settings
 */
data class AccountFullData(
    val account: Account,
    val boxesAndSettings: List<BoxAndSettings>
)