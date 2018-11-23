package com.stazis.subwaystationsmvvm.presentation.view.common

import android.annotation.SuppressLint
import android.content.Context
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.stazis.subwaystationsmvvm.R
import com.stazis.subwaystationsmvvm.helpers.hideSoftKeyboard
import com.stazis.subwaystationsmvvm.helpers.showSoftKeyboard
import org.jetbrains.anko.*

@SuppressLint("ViewConstructor")
class EditableTextView(context: Context, private val onTextUpdated: (String) -> Unit) :
    _RelativeLayout(context) {

    private lateinit var coverView: View
    private lateinit var text: EditTextWithFont
    private lateinit var edit: FloatingActionButton
    private lateinit var save: FloatingActionButton
    private lateinit var cancel: FloatingActionButton
    private var savedText = ""
    private var active = false
    private var inEditMode = false

    init {
        initUI()
        setListeners()
    }

    fun updateSavedText(savedText: String) {
        this.savedText = savedText
        text.setText(savedText)
        disableEditMode()
        active = true
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initUI() = relativeLayout {
        text = editTextWithFont("Montserrat-Regular") {
            id = R.id.editableTextViewText
            backgroundResource = android.R.color.transparent
            hint = resources.getString(R.string.enter_text)
            textSize = 16f
        }.lparams(matchParent) {
            leftOf(R.id.editableTextViewButtons)
        }
        verticalLayout {
            id = R.id.editableTextViewButtons
            edit = floatingActionButton {
                imageResource = R.drawable.ic_edit
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
        coverView = view().lparams(matchParent, matchParent)
    }

    private fun setListeners() {
        edit.setOnClickListener { enableEditMode() }
        cancel.setOnClickListener {
            disableEditMode()
            text.setText(savedText)
        }
        save.setOnClickListener {
            active = false
            onTextUpdated.invoke(text.text.toString())
        }
        with(text) {
            addTextChangedListener(object : TextWatcher {
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
            setOnClickListener {
                if (!inEditMode) {
                    enableEditMode()
                }
            }
        }
        coverView.setOnTouchListener { _, _ -> !active }
    }

    private fun enableEditMode() {
        inEditMode = true
        edit.hide()
        cancel.show()
        text.requestFocus()
        showSoftKeyboard(context, text)
    }

    private fun disableEditMode() {
        inEditMode = false
        cancel.hide()
        save.hide()
        edit.show()
        hideSoftKeyboard(context, text)
    }

    class EditableViewState(
        superState: Parcelable,
        val savedText: String,
        val editModeEnabled: Boolean,
        val saveShown: Boolean
    ) : View.BaseSavedState(superState)

    override fun onSaveInstanceState(): Parcelable? = EditableViewState(
        super.onSaveInstanceState()!!,
        savedText,
        inEditMode,
        save.isVisible
    )

    override fun onRestoreInstanceState(state: Parcelable) {
        if (state is EditableViewState) {
            super.onRestoreInstanceState(state.superState)
            restoreSavedState(state)
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    private fun restoreSavedState(state: EditableViewState) {
        savedText = state.savedText
        if (state.editModeEnabled) {
            enableEditMode()
            if (state.saveShown) {
                save.show()
            }
        } else {
            disableEditMode()
        }
    }
}