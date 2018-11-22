package com.stazis.subwaystationsmvvm.presentation.view.common

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.withStyledAttributes
import com.stazis.subwaystationsmvvm.R

class TextViewWithFont @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        context?.withStyledAttributes(attrs, R.styleable.TextViewWithFont) {
            setTypefaceIfNotNull(getString(R.styleable.TextViewWithFont_typeface))
        }
        includeFontPadding = false
    }

    private fun setTypefaceIfNotNull(typeface: String?) = typeface?.let {
        this.typeface = Typeface.createFromAsset(context.assets, "fonts/$typeface.ttf")
    }

    fun withTypeface(typeface: String?) = this.apply {
        setTypefaceIfNotNull(typeface)
    }
}