package com.alexeyyuditsky.test.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cat(
    val id: Int,
    val name: String,
    val description: String
) : Parcelable {

    override fun toString(): String = name

}