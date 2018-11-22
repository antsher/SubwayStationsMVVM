package com.stazis.subwaystationsmvvm.presentation.view.common

import android.content.Context
import android.os.Parcelable
import android.text.InputType
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import androidx.core.content.withStyledAttributes
import com.stazis.subwaystationsmvvm.R
import org.jetbrains.anko.dip
import org.jetbrains.anko.find

class InputView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    LinearLayout(context, attrs, defStyleAttr) {

    val label by lazy { find<TextViewWithFont>(R.id.inputViewLabel) }
    val text by lazy { find<EditTextWithFont>(R.id.inputViewText) }

    init {
        initUi()
        context?.withStyledAttributes(attrs, R.styleable.InputView) {
            label.text = getString(R.styleable.InputView_label) ?: ""
        }
    }

    private fun initUi() = linearLayoutWithState {
        orientation = VERTICAL
        layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        textViewWithFont("Montserrat-SemiBold") {
            id = R.id.inputViewLabel
            textSize = 24f
            with(LayoutParams(WRAP_CONTENT, WRAP_CONTENT)) {
                leftMargin = dip(5)
                bottomMargin = dip(5)
                layoutParams = this
            }
        }
        editTextWithFont("Montserrat-Regular") {
            id = R.id.inputViewText
            textSize = 18f
            inputType = InputType.TYPE_CLASS_TEXT
            maxLines = 1
            layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
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