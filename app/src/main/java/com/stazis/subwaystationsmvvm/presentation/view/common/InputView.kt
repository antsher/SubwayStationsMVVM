package com.stazis.subwaystationsmvvm.presentation.view.common

import android.annotation.SuppressLint
import android.content.Context
import android.os.Parcelable
import android.text.InputType
import android.view.View
import com.stazis.subwaystationsmvvm.R
import org.jetbrains.anko._LinearLayout
import org.jetbrains.anko.dip
import org.jetbrains.anko.find
import org.jetbrains.anko.matchParent

@SuppressLint("ViewConstructor")
class InputView(context: Context, label: String) : _LinearLayout(context) {

    val text by lazy { find<EditTextWithFont>(R.id.inputViewText) }

    init {
        initUI()
        find<TextViewWithFont>(R.id.inputViewLabel).text = label
    }

    private fun initUI() = linearLayoutWithState {
        lparams(matchParent) {
            orientation = VERTICAL
        }
        textViewWithFont("Montserrat-SemiBold") {
            id = R.id.inputViewLabel
            textSize = 24f
        }.lparams {
            bottomMargin = dip(5)
            leftMargin = dip(5)
        }
        editTextWithFont("Montserrat-Regular") {
            id = R.id.inputViewText
            inputType = InputType.TYPE_CLASS_TEXT
            maxLines = 1
            textSize = 18f
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