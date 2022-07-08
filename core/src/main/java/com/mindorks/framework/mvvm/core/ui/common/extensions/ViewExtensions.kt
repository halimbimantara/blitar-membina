package com.mindorks.framework.mvvm.core.ui.common.extensions

import android.view.View
import android.view.View.*
import android.widget.EditText
import android.widget.TextView

fun View.setVisibleOrGone(show: Boolean) {
    visibility = if (show) VISIBLE else GONE
}

fun View.setVisible(show: Boolean) {
    visibility = if (show) VISIBLE else INVISIBLE
}

fun EditText.etToString(): String {
    return this.text.toString()
}

fun TextView.etToString(): String {
    return this.text.toString()
}

fun EditText.etIsEmpty(): Boolean {
    return this.text.toString().isEmpty()
}
