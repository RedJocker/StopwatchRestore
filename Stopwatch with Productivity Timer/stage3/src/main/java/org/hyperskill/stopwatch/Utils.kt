package org.hyperskill.stopwatch

import android.util.DisplayMetrics
import android.util.TypedValue
import android.util.TypedValue.COMPLEX_UNIT_DIP

object Utils {

    fun Long.timeString(): String {
        val seconds = this % 60
        val minutes = this / 60
        return "%02d:%02d".format(minutes, seconds)
    }

    fun Int.dpsToPixels(displayMetrics: DisplayMetrics): Int {
        return TypedValue.applyDimension(
                COMPLEX_UNIT_DIP, this.toFloat(), displayMetrics
        ).toInt()
    }
}