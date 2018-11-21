package com.stazis.subwaystationsmvvm.presentation.view.common

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.withStyledAttributes
import com.stazis.subwaystationsmvvm.R
import kotlinx.android.synthetic.main.view_input.view.*

class InputView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    LinearLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_input, this, true)
        context?.withStyledAttributes(attrs, R.styleable.InputView) {
            label.text = getString(R.styleable.InputView_label) ?: ""
        }
    }

    class InputViewState(superState: Parcelable, var savedText: String) : View.BaseSavedState(superState)

    public override fun onSaveInstanceState(): Parcelable? =
        InputViewState(super.onSaveInstanceState()!!, text.text.toString())

    public override fun onRestoreInstanceState(state: Parcelable) = if (state is InputViewState) {
        super.onRestoreInstanceState(state.superState)
        text.setText(state.savedText)
    } else {
        super.onRestoreInstanceState(state)
    }
}