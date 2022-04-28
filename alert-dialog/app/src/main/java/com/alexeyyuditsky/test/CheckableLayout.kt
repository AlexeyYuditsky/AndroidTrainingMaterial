package com.alexeyyuditsky.test

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.Checkable
import androidx.constraintlayout.widget.ConstraintLayout

class CheckableLayout(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttrs: Int
) : ConstraintLayout(context, attrs, defStyleAttrs), Checkable {

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private val checkableChild: Checkable by lazy { findCheckableChild(this) }

    override fun isChecked(): Boolean = checkableChild.isChecked

    override fun toggle(): Unit = checkableChild.toggle()

    override fun setChecked(checked: Boolean) {
        checkableChild.isChecked = checked
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        val checkableView = checkableChild as View
        checkableView.isFocusableInTouchMode = false
        checkableView.isFocusable = false
        checkableView.isClickable = false
    }

    private fun findCheckableChild(root: ViewGroup): Checkable {
        for (i in 0 until root.childCount) {
            val child = root.getChildAt(i)
            if (child is Checkable) return child
            if (child is ViewGroup) return findCheckableChild(root)
        }
        throw IllegalStateException("Cannot find checkable child view")
    }

}