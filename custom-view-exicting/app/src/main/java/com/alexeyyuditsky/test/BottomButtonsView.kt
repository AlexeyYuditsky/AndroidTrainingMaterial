package com.alexeyyuditsky.test

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.alexeyyuditsky.test.databinding.PartButtonsBinding
import kotlin.properties.Delegates

enum class BottomButtonAction { NEGATIVE, POSITIVE }

typealias OnBottomButtonActionListener = (BottomButtonAction) -> Unit

class BottomButtonsView(
    context: Context,
    attr: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) : ConstraintLayout(context, attr, defStyleAttr, defStyleRes) {

    private val binding: PartButtonsBinding

    private var listener: OnBottomButtonActionListener? = null

    var bottomNegativeButtonText: String = ""
        set(value) {
            field = value
            binding.negativeButton.text = field
        }

    var bottomPositiveButtonText: String = ""
        set(value) {
            field = value
            binding.positiveButton.text = field
        }

    var bottomNegativeBackgroundColor: Int = Color.WHITE
        set(value) {
            field = value
            binding.negativeButton.backgroundTintList = ColorStateList.valueOf(field)
        }

    var bottomPositiveBackgroundColor: Int = Color.BLACK
        set(value) {
            field = value
            binding.positiveButton.backgroundTintList = ColorStateList.valueOf(field)
        }

    var isBottomProgressMode: Boolean = false
        set(value) {
            field = value
            binding.apply {
                if (field) {
                    negativeButton.visibility = GONE
                    positiveButton.visibility = GONE
                    progress.visibility = VISIBLE
                } else {
                    negativeButton.visibility = VISIBLE
                    positiveButton.visibility = VISIBLE
                    progress.visibility = GONE
                }
            }
        }

    constructor(context: Context, attr: AttributeSet?, defStyleAttr: Int)
            : this(context, attr, defStyleAttr, R.style.MyBottomButtonsStyle)

    constructor(context: Context, attr: AttributeSet?)
            : this(context, attr, R.attr.bottomButtonsStyle)

    constructor(context: Context) : this(context, null)

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.part_buttons, this, true)
        binding = PartButtonsBinding.bind(this)
        initAttributes(attr, defStyleAttr, defStyleRes)
        initListeners()
    }

    private fun initAttributes(attr: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        if (attr == null) return
        val typedArray = context.obtainStyledAttributes(
            attr,
            R.styleable.BottomButtonsView,
            defStyleAttr,
            defStyleRes
        )

        binding.apply {
            bottomNegativeButtonText =
                typedArray.getString(R.styleable.BottomButtonsView_bottomNegativeButtonText)
                    ?: context.getString(android.R.string.cancel)

            bottomPositiveButtonText =
                typedArray.getString(R.styleable.BottomButtonsView_bottomPositiveButtonText)
                    ?: context.getString(android.R.string.ok)

            bottomNegativeBackgroundColor = typedArray.getColor(
                R.styleable.BottomButtonsView_bottomNegativeBackgroundColor,
                Color.WHITE
            )

            bottomPositiveBackgroundColor = typedArray.getColor(
                R.styleable.BottomButtonsView_bottomPositiveBackgroundColor,
                Color.BLACK
            )

            isBottomProgressMode =
                typedArray.getBoolean(R.styleable.BottomButtonsView_bottomProgressMode, false)
        }

        typedArray.recycle()
    }

    private fun initListeners() {
        binding.negativeButton.setOnClickListener {
            this.listener?.invoke(BottomButtonAction.NEGATIVE)
        }
        binding.positiveButton.setOnClickListener {
            this.listener?.invoke(BottomButtonAction.POSITIVE)
        }
    }

    fun setListener(listener: OnBottomButtonActionListener?) {
        this.listener = listener
    }

    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        return SavedState(superState).apply {
            negativeButtonText = bottomNegativeButtonText
            positiveButtonText = bottomPositiveButtonText
            negativeButtonColor = bottomNegativeBackgroundColor
            positiveButtonColor = bottomPositiveBackgroundColor
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val savedState = state as SavedState
        super.onRestoreInstanceState(savedState.superState)
        bottomNegativeButtonText = savedState.negativeButtonText
        bottomPositiveButtonText = savedState.positiveButtonText
        bottomNegativeBackgroundColor = savedState.negativeButtonColor
        bottomPositiveBackgroundColor = savedState.positiveButtonColor
    }

    class SavedState : BaseSavedState {

        var negativeButtonText by Delegates.notNull<String>()
        var positiveButtonText by Delegates.notNull<String>()
        var negativeButtonColor by Delegates.notNull<Int>()
        var positiveButtonColor by Delegates.notNull<Int>()

        constructor(superState: Parcelable?) : super(superState)

        constructor(parcel: Parcel) : super(parcel) {
            negativeButtonText = parcel.readString()!!
            positiveButtonText = parcel.readString()!!
            negativeButtonColor = parcel.readInt()
            positiveButtonColor = parcel.readInt()
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeString(negativeButtonText)
            out.writeString(positiveButtonText)
            out.writeInt(negativeButtonColor)
            out.writeInt(positiveButtonColor)
        }

        private companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(source: Parcel): SavedState = SavedState(source)
                override fun newArray(size: Int): Array<SavedState?> = emptyArray()
            }
        }

    }

}