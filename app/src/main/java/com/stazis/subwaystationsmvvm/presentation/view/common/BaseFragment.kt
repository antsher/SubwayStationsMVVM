package com.stazis.subwaystationsmvvm.presentation.view.common

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.stazis.subwaystationsmvvm.R

abstract class BaseFragment : Fragment() {

    private lateinit var progressBar: View
    private lateinit var messageDialog: AlertDialog
    open lateinit var root: ViewGroup

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        progressBar = layoutInflater.inflate(R.layout.view_progress_bar, root, false).apply { root.addView(this) }
    }

    fun showDialog(title: String, message: String) {
        if (!::messageDialog.isInitialized || !messageDialog.isShowing) {
            messageDialog = AlertDialog.Builder(context)
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