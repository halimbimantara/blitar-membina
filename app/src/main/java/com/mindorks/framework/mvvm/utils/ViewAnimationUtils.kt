package com.mindorks.framework.mvvm.utils

import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation

/**
 * Created by RV on 06/09/17.
 */
object ViewAnimationUtils {
    fun scaleAnimateView(view: View) {
        val animation = ScaleAnimation(
            1.15f, 1F, 1.15f, 1F,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        view.animation = animation
        animation.setDuration(100)
        animation.start()
    }
}