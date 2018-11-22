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
        linearLayoutWithState {
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

//<com.stazis.subwaystationsmvvm.presentation.view.common.state.LinearLayoutWithState xmlns:android="http://schemas.android.com/apk/res/android"
//xmlns:app="http://schemas.android.com/apk/res-auto"
//android:layout_width="match_parent"
//android:layout_height="wrap_content"
//android:orientation="vertical">
//
//<com.stazis.subwaystationsmvvm.presentation.view.common.TextViewWithFont
//android:id="@+id/label"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:layout_marginBottom="5dp"
//android:textSize="24sp"
//app:typeface="Montserrat-SemiBold" />
//
//<com.stazis.subwaystationsmvvm.presentation.view.common.EditTextWithFont
//android:id="@+id/text"
//android:layout_width="match_parent"
//android:layout_height="wrap_content"
//android:inputType="text"
//android:maxLines="1"
//android:textSize="18sp"
//app:typeface="Montserrat-Regular" />
//</com.stazis.subwaystationsmvvm.presentation.view.common.state.LinearLayoutWithState>