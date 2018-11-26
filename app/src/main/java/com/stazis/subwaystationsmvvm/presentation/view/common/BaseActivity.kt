package com.stazis.subwaystationsmvvm.presentation.view.common

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.stazis.subwaystationsmvvm.R
import com.stazis.subwaystationsmvvm.presentation.vm.BaseViewModel

abstract class BaseActivity : AppCompatActivity() {

    abstract val vm: BaseViewModel
    private lateinit var progressBar: View
    private lateinit var messageDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inflateProgressBar()
    }

    private fun inflateProgressBar() = with(findViewById<ViewGroup>(android.R.id.content)) {
        progressBar = layoutInflater.inflate(R.layout.view_progress_bar, this, false).also {
            addView(it)
        }
    }

    open fun bindViewModel() {
        vm.loading.observe(this, Observer(this::showBoundProgressBar))
        vm.message.observe(this, Observer(this::showBoundDialog))
    }

    private fun showBoundProgressBar(isVisible: Boolean) {
        progressBar.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun showBoundDialog(message: String) {
        if (message != "") {
            if (!::messageDialog.isInitialized || !messageDialog.isShowing) {
                messageDialog = AlertDialog.Builder(this)
                    .setNeutralButton("OK") { dialog, _ -> dialog.dismiss() }
                    .setMessage(message)
                    .setOnDismissListener { vm.message.value = "" }
                    .create()
                    .apply { show() }
            }
        }
    }
}