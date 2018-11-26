package com.stazis.subwaystationsmvvm.presentation.view.common

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.stazis.subwaystationsmvvm.R
import com.stazis.subwaystationsmvvm.presentation.vm.BaseViewModel

abstract class BaseFragment : Fragment() {

    abstract val vm: BaseViewModel
    private lateinit var progressBar: View
    private lateinit var messageDialog: AlertDialog
    open lateinit var root: ViewGroup

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        progressBar = layoutInflater.inflate(R.layout.view_progress_bar, root, false).apply { root.addView(this) }
    }

    open fun bindViewModel() {
        vm.loading.observe(this, Observer(this::showBoundProgressBar))
        vm.message.observe(this, Observer(this::showBoundDialog))
    }

    private fun showBoundProgressBar(isVisible: Boolean) {
        progressBar.visibility = if (isVisible) VISIBLE else GONE
    }

    private fun showBoundDialog(message: String) {
        if (message != "") {
            if (!::messageDialog.isInitialized || !messageDialog.isShowing) {
                messageDialog = AlertDialog.Builder(context)
                    .setNeutralButton("OK") { dialog, _ -> dialog.dismiss() }
                    .setMessage(message)
                    .setOnDismissListener { vm.message.value = "" }
                    .create()
                    .apply { show() }
            }
        }
    }
}