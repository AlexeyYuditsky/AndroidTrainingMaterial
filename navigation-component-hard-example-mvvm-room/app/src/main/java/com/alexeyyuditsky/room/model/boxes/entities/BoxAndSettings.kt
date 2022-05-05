package com.alexeyyuditsky.room.model.boxes.entities

import com.alexeyyuditsky.room.model.boxes.entities.Box

data class BoxAndSettings(
    val box: Box,
    val isActive: Boolean
)