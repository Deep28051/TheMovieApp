package com.appintuitions.rvkotlin.util

import android.content.Context
import kotlin.jvm.JvmOverloads
import androidx.appcompat.widget.AppCompatTextView
import android.graphics.Typeface
import android.util.AttributeSet
import com.appintuitions.rvkotlin.util.SFTextView

class SFTextView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatTextView(
    context!!, attrs, defStyle
) {
    companion object {
        private var mTypeface: Typeface? = null
    }

    init {
        if (mTypeface == null) {
            mTypeface = Typeface.createFromAsset(this.resources.assets, "font/font1.ttf")
        }
        typeface = mTypeface
    }
}