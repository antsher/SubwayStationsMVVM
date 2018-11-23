package com.stazis.subwaystationsmvvm.presentation.view.general.pager

import android.content.Context
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.maps.model.LatLng
import com.stazis.subwaystationsmvvm.R
import com.stazis.subwaystationsmvvm.model.entities.Station
import com.stazis.subwaystationsmvvm.presentation.view.common.TextViewWithFont
import com.stazis.subwaystationsmvvm.presentation.view.common.textViewWithFont
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.viewPager

class StationViewPager(context: Context) : _LinearLayout(context) {

    private lateinit var title: TextViewWithFont
    private lateinit var pager: ViewPager

    init {
        verticalLayout {
            padding = dip(10)
            relativeLayout {
                imageButton {
                    id = R.id.stationViewPagerScrollLeft
                    contentDescription = resources.getString(R.string.scroll_left)
                    imageResource = R.drawable.ic_arrow_left
                }.setOnClickListener {
                    pager.currentItem -= 1
                }
                title = textViewWithFont("Montserrat-SemiBold") {
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                    textSize = 24f
                }.lparams(matchParent) {
                    startOf(R.id.stationViewPagerScrollRight)
                    endOf(R.id.stationViewPagerScrollLeft)
                    centerVertically()
                }
                imageButton {
                    id = R.id.stationViewPagerScrollRight
                    contentDescription = resources.getString(R.string.scroll_right)
                    imageResource = R.drawable.ic_arrow_right
                }.lparams {
                    alignParentEnd()
                }.setOnClickListener {
                    pager.currentItem += 1
                }
            }.lparams(matchParent) {
                margin = dip(10)
            }
            pager = viewPager {
                id = R.id.stationViewPagerPager
            }.lparams(matchParent, matchParent)
        }
    }

    fun initialize(fragmentManager: FragmentManager, stations: List<Station>, location: LatLng) {
        val pager = findViewById<ViewPager>(R.id.stationViewPagerPager)
        pager.adapter = StationPagerAdapter(fragmentManager, stations, location)
        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                title.text = pager.adapter!!.getPageTitle(position)
            }
        })
        title.text = (pager.adapter as StationPagerAdapter).getPageTitle(pager.currentItem)
    }
}