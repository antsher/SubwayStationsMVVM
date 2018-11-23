package com.stazis.subwaystationsmvvm.presentation.view.common

import android.content.Context
import android.os.Parcelable
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.stazis.subwaystationsmvvm.R
import com.stazis.subwaystationsmvvm.helpers.hideSoftKeyboard
import com.stazis.subwaystationsmvvm.helpers.showSoftKeyboard
import org.jetbrains.anko.*

class EditableTextView(context: Context) : _RelativeLayout(context) {

    private lateinit var text: EditTextWithFont
    private lateinit var edit: FloatingActionButton
    private lateinit var save: FloatingActionButton
    private lateinit var cancel: FloatingActionButton

    var onTextUpdated = { }
    var savedText: String = ""
        set(string) {
            field = string
            text.setText(string)
        }
    private var editModeEnabled = false

    init {
        relativeLayout {
            text = editTextWithFont("Montserrat-Regular") {
                backgroundResource = android.R.color.transparent
                freezesText = true
                hint = resources.getString(R.string.enter_text)
                inputType = InputType.TYPE_NULL
                textSize = 16f
            }.lparams(matchParent)
            verticalLayout {
                id = R.id.editableTextViewButtons
                edit = floatingActionButton {
                    imageResource = R.drawable.ic_edit
                    isGone = true
                }.lparams {
                    margin = dip(5)
                }
                save = floatingActionButton {
                    imageResource = R.drawable.ic_check
                    isGone = true
                }.lparams {
                    margin = dip(5)
                }
                cancel = floatingActionButton {
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
//            TODO: fix a bug which doesn't enable save after rotation
        } else {
            disableEditMode()
        }
    } else {
        super.onRestoreInstanceState(state)
    }
}