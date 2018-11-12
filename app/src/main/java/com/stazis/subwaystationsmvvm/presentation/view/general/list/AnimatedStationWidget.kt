package com.stazis.subwaystationsmvvm.presentation.view.general.list

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.stazis.subwaystationsmvvm.R
import com.stazis.subwaystationsmvvm.model.entities.Station
import kotlinx.android.synthetic.main.view_station.view.*

@SuppressLint("ViewConstructor")
class AnimatedStationWidget(
    context: Context?,
    var expanded: Boolean,
    station: Station,
    stationDistance: Int,
    onClicked: () -> Unit
) : RelativeLayout(context) {

    companion object {

        private const val ANIMATION_DURATION = 300L
    }

    val stationName = station.name
    private var animationInProgress = false
    private val dpRatio = resources.displayMetrics.density

    init {
        LayoutInflater.from(context).inflate(R.layout.view_station, this, true)
        restoreExpanded()
        name.text = stationName
        latitude.text = String.format("Latitude: %f", station.latitude)
        longitude.text = String.format("Longitude: %f", station.longitude)
        distance.text = String.format("%dm", stationDistance)
        switchState.setOnClickListener { switchState() }
        setOnClickListener { onClicked() }
    }

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