package com.stazis.subwaystationsmvvm.presentation.view.common

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.withStyledAttributes
import com.stazis.subwaystationsmvvm.R

class EditTextWithFont @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null) :
    AppCompatEditText(context, attrs) {

    init {
        context?.withStyledAttributes(attrs, R.styleable.EditTextWithFont) {
            getString(R.styleable.EditTextWithFont_typeface)?.let {
                typeface = Typeface.createFromAsset(context.assets, "fonts/$it.ttf")
            }
        }
        includeFontPadding = false
    }
}