package com.alexeyyuditsky.test.screens.main.tabs.settings

import com.alexeyyuditsky.test.model.boxes.entities.Box

data class BoxSetting(
    val box: Box,
    val enabled: Boolean
)