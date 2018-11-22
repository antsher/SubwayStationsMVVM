package com.stazis.subwaystationsmvvm.presentation.view.common

import android.content.Context
import android.os.Parcelable
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.stazis.subwaystationsmvvm.R
import com.stazis.subwaystationsmvvm.helpers.hideSoftKeyboard
import com.stazis.subwaystationsmvvm.helpers.showSoftKeyboard
import org.jetbrains.anko.*

class EditableTextView(context: Context) : _RelativeLayout(context) {

    private val text by lazy { findViewById<EditTextWithFont>(R.id.editableTextViewText) }
    private val edit by lazy { findViewById<FloatingActionButton>(R.id.editableTextViewEdit) }
    private val save by lazy { findViewById<FloatingActionButton>(R.id.editableTextViewSave) }
    private val cancel by lazy { findViewById<FloatingActionButton>(R.id.editableTextViewCancel) }

    var onTextUpdated = { }
    var savedText: String = ""
        set(string) {
            field = string
            text.setText(string)
        }
    private var editModeEnabled = false

    init {
        relativeLayout {
            editTextWithFont("Montserrat-Regular") {
                backgroundResource = android.R.color.transparent
                freezesText = true
                hint = resources.getString(R.string.enter_text)
                inputType = InputType.TYPE_NULL
                id = R.id.editableTextViewText
                textSize = 16f
            }.lparams(matchParent)
            linearLayout {
                id = R.id.editableTextViewButtons
                orientation = LinearLayout.VERTICAL
                floatingActionButton {
                    id = R.id.editableTextViewEdit
                    imageResource = R.drawable.ic_edit
                    isGone = true
                }.lparams {
                    margin = dip(5)
                }
                floatingActionButton {
                    id = R.id.editableTextViewSave
                    imageResource = R.drawable.ic_check
                    isGone = true
                }.lparams {
                    margin = dip(5)
                }
                floatingActionButton {
                    id = R.id.editableTextViewCancel
                    imageResource = R.drawable.ic_cross
                    isGone = true
                }.lparams {
                    margin = dip(5)
                }
            }.lparams {
                alignParentEnd()
            }
        }
    }

    fun enable() {
        edit.isVisible = true
        edit.setOnClickListener { enableEditMode() }
        cancel.setOnClickListener {
            disableEditMode()
            text.setText(savedText)
        }
        save.setOnClickListener { save() }
        text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString() != savedText) {
                    save.show()
                } else {
                    save.hide()
                }
            }
        })
    }

    private fun enableEditMode() {
        editModeEnabled = true
        edit.hide()
        cancel.show()
        text.run {
            setText(savedText)
            inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE
            isFocusableInTouchMode = true
            requestFocus()
        }
        showSoftKeyboard(context, text)
    }

    private fun disableEditMode() {
        editModeEnabled = false
        cancel.hide()
        save.hide()
        edit.show()
        text.inputType = InputType.TYPE_NULL
        hideSoftKeyboard(context, text)
    }

    private fun save() {
        savedText = text.text.toString()
        disableEditMode()
        onTextUpdated.invoke()
    }

    class EditableViewState(superState: Parcelable, var savedText: String, var editModeEnabled: Boolean) :
        View.BaseSavedState(superState)

    public override fun onSaveInstanceState(): Parcelable? =
        EditableViewState(super.onSaveInstanceState()!!, text.text.toString(), editModeEnabled)

    public override fun onRestoreInstanceState(state: Parcelable) = if (state is EditableViewState) {
        super.onRestoreInstanceState(state.superState)
        savedText = state.savedText
        if (state.editModeEnabled) {
            enableEditMode()
        } else {
            disableEditMode()
        }
    } else {
        super.onRestoreInstanceState(state)
    }
}