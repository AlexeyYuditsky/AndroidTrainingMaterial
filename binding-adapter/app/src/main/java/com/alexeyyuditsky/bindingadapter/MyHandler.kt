package com.alexeyyuditsky.bindingadapter

import android.graphics.Color
import android.view.View
import android.widget.TextView

class MyHandler {

    fun onDelete(view: View, employee: Employee) {
        if (view is TextView) {
            if (view.currentTextColor == -11975345)
                view.setTextColor(Color.WHITE)
            else if (view.currentTextColor == Color.WHITE)
                view.setTextColor(-11975345)
        }
    }

}