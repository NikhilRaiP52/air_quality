package com.sample.aqm.ui.bindingadapter

import android.view.View
import androidx.databinding.BindingAdapter
import com.sample.aqm.R


@BindingAdapter("aqiValue")
fun updatedBackground(view: View, aqiValue: Double?) {
    when(aqiValue?.toInt()) {
        in 0..50 -> view.setBackgroundColor(view.resources.getColor(R.color.aq_good))
        in 51..100 -> view.setBackgroundColor(view.resources.getColor(R.color.aq_satisfactory))
        in 101..200 -> view.setBackgroundColor(view.resources.getColor(R.color.aq_moderate))
        in 201..300 -> view.setBackgroundColor(view.resources.getColor(R.color.aq_poor))
        in 301..400 -> view.setBackgroundColor(view.resources.getColor(R.color.aq_very_poor))
        else -> view.setBackgroundColor(view.resources.getColor(R.color.aq_sever))
    }

}