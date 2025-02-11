package com.walhalla.ytlib.ui

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class StretchedImageView : AppCompatImageView {
    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context!!, attrs, defStyle) {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val imageHeight: Float
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        imageHeight = if (drawable != null) {
            measuredWidth.toFloat() / drawable.intrinsicWidth.toFloat() * drawable.intrinsicHeight.toFloat()
        } else {
            measuredHeight.toFloat()
        }
        setMeasuredDimension(measuredWidth, imageHeight.toInt())
    }
}