package com.holyquran.alquran.common

import android.view.View
import androidx.core.view.isVisible

fun View.isVisible() {
    this.visibility = if (this.isVisible) View.GONE else View.VISIBLE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}