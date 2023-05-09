package androidx.appcompat.widget

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.appcompat.widget.PopupMenu

@SuppressLint("RestrictedApi")
class CustomPopupMenu(
    context: Context,
    anchor: View
) : PopupMenu(context, anchor) {

    init {
        mPopup.setForceShowIcon(true)
    }

}