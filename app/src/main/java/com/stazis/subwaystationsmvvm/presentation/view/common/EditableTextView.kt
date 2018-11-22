package com.stazis.subwaystationsmvvm.presentation.view.common

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.stazis.subwaystationsmvvm.R
import com.stazis.subwaystationsmvvm.helpers.hideSoftKeyboard
import com.stazis.subwaystationsmvvm.helpers.showSoftKeyboard
import org.jetbrains.anko.*

class EditableTextView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null) :
    RelativeLayout(context, attrs) {

    companion object {

        private const val SUPER_STATE_KEY = "SUPER_STATE_KEY"
        private const val SAVED_TEXT_KEY = "SAVED_TEXT_KEY"
        private const val EDIT_MODE_ENABLED_KEY = "EDIT_MODE_ENABLED_KEY"
    }

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
        initUI()
        text.inputType = InputType.TYPE_NULL
    }

    private fun initUI() = relativeLayout {
        editTextWithFont("Montserrat-Regular") {
            backgroundResource = android.R.color.transparent
            freezesText = true
            hint = resources.getString(R.string.enter_text)
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
            inputType = InputType.TYPE_CLASS_TEXT
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

    override fun onSaveInstanceState() = bundleOf(
        SUPER_STATE_KEY to super.onSaveInstanceState(),
        SAVED_TEXT_KEY to savedText,
        EDIT_MODE_ENABLED_KEY to editModeEnabled
    )

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is Bundle) {
            super.onRestoreInstanceState(state.getParcelable(SUPER_STATE_KEY))
            state.getString(SAVED_TEXT_KEY)?.let { savedText = it }
            if (state.getBoolean(EDIT_MODE_ENABLED_KEY)) {
                enableEditMode()
            } else {
                disableEditMode()
            }
        } else {
            super.onRestoreInstanceState(state)
        }
    }
}