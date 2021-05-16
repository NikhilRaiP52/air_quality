package com.sample.aqm.utils

import android.util.Log

object AqiLog {
    private val isLogEnabled = true

    fun d(tag : String, message : String) {
        if (isLogEnabled) {
            Log.d(tag, message)
        }
    }
}