package com.sample.aqm.utils

import android.view.View
import java.math.BigDecimal
import java.math.RoundingMode

fun Double.twoDigits(): Double {
    return BigDecimal(this).setScale(2, RoundingMode.HALF_EVEN).toDouble()
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}