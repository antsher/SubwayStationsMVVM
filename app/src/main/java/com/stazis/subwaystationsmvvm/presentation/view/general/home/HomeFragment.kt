package com.stazis.subwaystationsmvvm.presentation.view.general.home

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.stazis.subwaystationsmvvm.R
import com.stazis.subwaystationsmvvm.presentation.view.common.inputView
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = UI {
        scrollView {
            linearLayout {
                orientation = LinearLayout.VERTICAL
                topPadding = dip(20)
                inputView(resources.getString(R.string.name)) {
                    id = R.id.homeFragmentName
                }.lparams(matchParent) {
                    bottomMargin = dip(10)
                }
                inputView(resources.getString(R.string.description)) {
                    id = R.id.homeFragmentDescription
                }.lparams(matchParent) {
                    bottomMargin = dip(10)
                }
                inputView(resources.getString(R.string.message)) {
                    id = R.id.homeFragmentMessage
                }.lparams(matchParent) {
                    bottomMargin = dip(10)
                }
                button {
                    text = resources.getString(R.string.next)
                    textSize = 30f
                }.lparams {
                    gravity = Gravity.CENTER
                }.setOnClickListener {
                    findNavController().navigate(R.id.station_map_dest)
                }
            }.lparams(matchParent) {
                marginStart = dip(10)
                marginEnd = dip(10)
            }
        }
    }.view
}