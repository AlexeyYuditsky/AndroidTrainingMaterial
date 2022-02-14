package com.alexeyyuditsky.test.foundation.views

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/** Base class for defining screen arguments.
 * Please note that all fields inside the screen should be Parcelable */
@Parcelize
open class BaseScreen : Parcelable