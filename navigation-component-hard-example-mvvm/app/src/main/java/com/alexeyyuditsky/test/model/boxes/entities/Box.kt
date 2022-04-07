package com.alexeyyuditsky.test.model.boxes.entities

import androidx.annotation.StringRes

class Box(
    val id: Int,
    @StringRes val colorNameRes: Int,
    val colorValue: Int
)