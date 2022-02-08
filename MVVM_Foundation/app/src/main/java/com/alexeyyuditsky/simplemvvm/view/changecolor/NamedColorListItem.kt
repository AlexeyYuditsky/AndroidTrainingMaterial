package com.alexeyyuditsky.simplemvvm.view.changecolor

import com.alexeyyuditsky.simplemvvm.model.colors.NamedColor

/**
 * Represents list item for the color; it may be selected or not
 */
data class NamedColorListItem(
    val namedColor: NamedColor,
    val selected: Boolean
)