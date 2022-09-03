package org.hyperskill.stopwatch

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.ProgressBar

class CustomProgressBar(context: Context) : ProgressBar(context) {
    private val colors = listOf(
            Color.RED,
            Color.LTGRAY,
            Color.GREEN,
            Color.DKGRAY,
    )

    private var currentColorIndex = 0

    init {
        nextColor()
    }

    @SuppressLint("NewApi")
    fun nextColor() {
        indeterminateTintList =
                ColorStateList.valueOf(colors[currentColorIndex])
                .also {
                    currentColorIndex = (currentColorIndex + 1) % colors.size
                }
    }
}