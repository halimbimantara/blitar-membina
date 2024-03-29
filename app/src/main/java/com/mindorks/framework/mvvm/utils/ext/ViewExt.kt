package com.mindorks.framework.mvvm.utils.ext

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mindorks.framework.mvvm.core.R

fun EditText.onFocusChanged(hasFocus: (Boolean) -> Unit) {
    this.setOnFocusChangeListener { _, b -> hasFocus(b) }
}

fun EditText.OnTextChangedListener(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {}

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            p0?.let {
                afterTextChanged(editableText.toString())
            }
        }

    })
}

/** makes visible a view. */
fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

/** makes gone a view. */
fun View.gone() {
    visibility = View.GONE
}

fun Context.color(resource: Int): Int {
    return ContextCompat.getColor(this, resource)
}

fun Fragment.color(resource: Int): Int {
    context?.let {
        return ContextCompat.getColor(it, resource)
    }
    return 0
}

fun RecyclerView.ViewHolder.getString(@StringRes string: Int): String {
    return itemView.context.getString(string)
}


fun RecyclerView.ViewHolder.getString(@StringRes string: Int, vararg arg: String): String {
    return itemView.context.getString(string, *arg)
}

fun RecyclerView.ViewHolder.color(@ColorRes resource: Int): Int {
    return itemView.context.color(resource)
}


fun ImageView.loadUrlPicture(link: String) =
    Glide.with(this).load(link)
        .placeholder(com.mindorks.framework.mvvm.R.drawable.ef_image_placeholder)
        .into(this)
