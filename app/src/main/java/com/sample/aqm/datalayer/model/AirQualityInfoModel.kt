package com.sample.aqm.datalayer.model

import androidx.annotation.Keep
import com.sample.aqm.utils.twoDigits
import java.util.*

@Keep
data class AirQualityInfoModel(
    val city: String = "",
    val aqi: Double = 0.0,
    val timestamp: Long = Calendar.getInstance().timeInMillis) {

    fun getAqInto2Digits(): String {
        return aqi.twoDigits().toString()
    }

    fun getUpdatedTime(): String {
        val currentCalendar = Calendar.getInstance()
        val seconds = (currentCalendar.timeInMillis - timestamp) / 1000
        val s = seconds % 60
        val m = seconds / 60 % 60
        val h = seconds / (60 * 60) % 24
        return when {
            h > 0 -> if (h.toInt() == 1) "$h hour ago" else "$h hours ago"
            m > 0 -> if (m.toInt() == 1) "$m minute ago" else "$m minutes ago"
            s >= 0 -> if (s.toInt() in 0..1) "Just updated" else if (s.toInt() < 5) "A few seconds ago" else "$s seconds ago"
            else -> "Some time ago"
        }
    }
}