package com.gabriel.traveladvisor.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun View.visible() {
    visibility = View.VISIBLE
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

private fun View.hide(hidingStrategy: Int) {
    visibility = hidingStrategy
}

fun View.gone() {
    hide(View.GONE)
}
