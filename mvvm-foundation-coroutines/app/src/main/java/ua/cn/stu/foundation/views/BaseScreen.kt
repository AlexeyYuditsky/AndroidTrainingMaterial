package ua.cn.stu.foundation.views

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Base class for defining screen arguments.
 * Please note that all fields inside the screen should be parcelable.
 */
@Parcelize
open class BaseScreen : Parcelable {

    companion object {
        const val ARG_SCREEN = "ARG_SCREEN"
    }

}
