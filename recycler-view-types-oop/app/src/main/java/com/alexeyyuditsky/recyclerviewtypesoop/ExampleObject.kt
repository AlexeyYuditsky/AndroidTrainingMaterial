package com.alexeyyuditsky.recyclerviewtypesoop

import android.widget.CheckBox
import android.widget.TextView

sealed class ExampleObject {

    open fun map(textView: TextView) = Unit
    open fun map(checkBox: CheckBox, textView: TextView) = Unit

    data class TypeA(
        private val title: String
    ) : ExampleObject() {
        override fun map(textView: TextView) {
            textView.text = title
        }
    }

    data class TypeB(
        private val url: String,
        private val checked: Boolean
    ) : ExampleObject() {
        override fun map(checkBox: CheckBox, textView: TextView) {
            textView.text = url
            checkBox.isChecked = checked
        }
    }
}
