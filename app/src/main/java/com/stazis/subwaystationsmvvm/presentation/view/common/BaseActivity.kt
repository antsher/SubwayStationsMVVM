package com.stazis.subwaystationsmvvm.presentation.view.common

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.stazis.subwaystationsmvvm.R

abstract class BaseActivity : AppCompatActivity() {

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

    fun showDialog(title: String, message: String) {
        if (!::messageDialog.isInitialized || !messageDialog.isShowing) {
            messageDialog = AlertDialog.Builder(this)
                .setTitle(title)
                .setNeutralButton("OK") { dialog, _ -> dialog.dismiss() }
                .setMessage(message)
                .create()
                .apply { show() }
        }
    }

    fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    fun hideLoading() {
        progressBar.visibility = View.GONE
    }
}