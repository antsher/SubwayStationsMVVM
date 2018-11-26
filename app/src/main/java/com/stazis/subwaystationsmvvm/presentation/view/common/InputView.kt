package com.stazis.subwaystationsmvvm.presentation.view.common

import android.annotation.SuppressLint
import android.content.Context
import android.os.Parcelable
import android.text.InputType
import android.view.View
import com.stazis.subwaystationsmvvm.presentation.view.common.extensions.bigTextViewWithFont
import com.stazis.subwaystationsmvvm.presentation.view.common.extensions.normalEditTextWithFont
import org.jetbrains.anko._LinearLayout
import org.jetbrains.anko.dip
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.verticalLayout

@SuppressLint("ViewConstructor")
class InputView(context: Context, label: String) : _LinearLayout(context) {

    private lateinit var label: TextViewWithFont
    private lateinit var text: EditTextWithFont

    init {
        initUI()
        this.label.text = label
    }

    private fun initUI() = verticalLayout {
        lparams(matchParent)
        label = bigTextViewWithFont("Montserrat-SemiBold").lparams {
            bottomMargin = dip(5)
            leftMargin = dip(5)
        }
        text = normalEditTextWithFont("Montserrat-Regular") {
            inputType = InputType.TYPE_CLASS_TEXT
            maxLines = 1
        }.lparams(matchParent)
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