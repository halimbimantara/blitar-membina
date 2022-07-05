package com.mindorks.framework.mvvm.utils.ext

import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.mindorks.framework.mvvm.R

fun ImageView.loadImage(url: String, placeholder: Drawable) =
    Glide.with(this).load(url).placeholder(placeholder).into(this)

fun ImageView.loadImage(url: String) =
    Glide.with(this).load(url).into(this)

fun ImageView.loadImageUri(assetpath: String) =
    Glide.with(this).load(
        Uri.parse("file:///android_asset/$assetpath")
    )
        .placeholder(R.drawable.ic_baseline_image_24)
        .into(this)

fun ImageView.loadImageDrawable(drawable: Int) =
    Glide.with(this).load(drawable)
        .placeholder(R.drawable.ic_baseline_image_24)
        .into(this)

fun ImageView.loadImageRoundVehicle(url: Int) =
    Glide.with(this).load(url)
        .placeholder(R.drawable.ic_baseline_image_24)
        .circleCrop()
        .into(this)

fun ImageView.loadImageRound(url: String) =
    Glide.with(this).load(url)
        .placeholder(R.drawable.ic_baseline_image_24)
        .circleCrop()
        .into(this)


fun ImageView.loadImageWithError(url: String, errorDrawable: Int) {
    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.ic_baseline_image_24)
        .error(errorDrawable)
        .into(this)
}

fun ImageView.loadImageCircleWithError(url: String, errorDrawable: Int) {
    Glide.with(this)
        .load(url)
        .circleCrop()
        .error(errorDrawable)
        .into(this)
}

fun ImageView.loadImageCircleWithCenterCrop(url: String, errorDrawable: Int) {
    Glide.with(this)
        .load(url)
        .circleCrop()
        .centerCrop()
        .error(errorDrawable)
        .into(this)
}
