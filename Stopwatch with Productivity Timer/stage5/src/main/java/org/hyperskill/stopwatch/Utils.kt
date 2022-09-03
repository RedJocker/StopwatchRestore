package org.hyperskill.stopwatch

import android.app.Activity
import android.app.NotificationManager
import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import android.util.TypedValue.COMPLEX_UNIT_DIP
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat


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

    fun Activity.resId(idString: String): Int {
        return resources.getIdentifier(
                idString, "id", packageName
        )
    }


}