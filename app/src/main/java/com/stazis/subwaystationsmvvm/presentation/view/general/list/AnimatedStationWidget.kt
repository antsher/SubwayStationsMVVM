package com.stazis.subwaystationsmvvm.presentation.view.general.list

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.LinearLayout
import com.stazis.subwaystationsmvvm.R
import com.stazis.subwaystationsmvvm.model.entities.Station
import com.stazis.subwaystationsmvvm.presentation.view.common.textViewWithFont
import org.jetbrains.anko.*

@SuppressLint("ViewConstructor")
class AnimatedStationWidget(
    context: Context,
    var expanded: Boolean,
    station: Station,
    stationDistance: Int,
    onClicked: () -> Unit
) : _RelativeLayout(context) {

    companion object {

        private const val ANIMATION_DURATION = 300L
    }

    private lateinit var hiddenView: LinearLayout
    val stationName = station.name
    private var animationInProgress = false
    private val dpRatio = resources.displayMetrics.density

    init {
        initUI(station, stationDistance, onClicked)
        restoreExpanded()
    }

    private fun initUI(station: Station, stationDistance: Int, onClicked: () -> Unit) = relativeLayout {
        verticalLayout {
            textViewWithFont("Montserrat-SemiBold") {
                text = stationName
                textSize = 24f
            }.lparams {
                topMargin = dip(10)
            }
            textViewWithFont("Montserrat-Light") {
                text = String.format("%d%s", stationDistance, resources.getString(R.string.meter_short))
                textSize = 16f
            }.lparams {
                topMargin = dip(5)
            }
            hiddenView = verticalLayout {
                alpha = 0f
                translationY = dip(-50).toFloat()
                visibility = View.GONE
                textViewWithFont("Montserrat-Italic") {
                    text = String.format("%s %f", resources.getString(R.string.latitude), station.latitude)
                    textSize = 18f
                }.lparams {
                    bottomMargin = dip(5)
                }
                textViewWithFont("Montserrat-Italic") {
                    text = String.format("%s %f", resources.getString(R.string.longitude), station.longitude)
                    textSize = 18f
                }
            }.lparams(matchParent) {
                topMargin = dip(10)
            }
        }.lparams(matchParent) {
            marginStart = dip(15)
            bottomMargin = dip(5)
            startOf(R.id.animatedStationWidgetSwitchState)
        }
        imageView {
            id = R.id.animatedStationWidgetSwitchState
            contentDescription = resources.getString(R.string.expand_info)
            padding = dip(10)
            imageResource = R.drawable.ic_arrow_bottom
        }.lparams(dip(68), dip(68)) {
            alignParentEnd()
        }.setOnClickListener { switchState() }
    }.setOnClickListener { onClicked() }

    private fun restoreExpanded() {
        if (expanded) {
            hiddenView.visibility = VISIBLE
            hiddenView.alpha = 1f
            hiddenView.translationY = 0f
        }
    }

    private fun switchState() = ifNotAnimationInProgress {
        if (expanded) {
            collapse()
        } else {
            expand()
        }
    }

    private inline fun ifNotAnimationInProgress(f: () -> Unit) {
        if (!animationInProgress) {
            f()
        }
    }

    private fun expand() = hiddenView.animate()
        .alpha(1f)
        .translationY(0f)
        .setDuration(ANIMATION_DURATION)
        .setListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                hiddenView.visibility = VISIBLE
                animationInProgress = true
                expanded = true
            }

            override fun onAnimationEnd(animation: Animator) {
                animationInProgress = false
            }

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}
        })
        .start()

    private fun collapse() = hiddenView.animate()
        .alpha(0f)
        .translationY(-50 * dpRatio)
        .setDuration(ANIMATION_DURATION)
        .setListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                animationInProgress = true
                expanded = false
            }

            override fun onAnimationEnd(animation: Animator) {
                animationInProgress = false
                hiddenView.visibility = GONE
            }

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}
        })
        .start()
}