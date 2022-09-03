package org.hyperskill.stopwatch

import android.app.AlertDialog
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.CHAIN_PACKED
import androidx.core.view.setPadding
import org.hyperskill.stopwatch.Utils.dpsToPixels
import org.hyperskill.stopwatch.Utils.resId

class MainActivityLayout(private val mainActivity: MainActivity):  ConstraintLayout(mainActivity) {

    private val parentId = mainActivity.resId("mainLayout")
    private val textViewId = mainActivity.resId("textView")
    private val resetButtonId = mainActivity.resId("resetButton")
    private val startButtonId = mainActivity.resId("startButton")
    private val settingsButtonId = mainActivity.resId("settingsButton")
    private val progressBarId =  mainActivity.resId("progressBar")


    val textView = TextView(mainActivity).apply {
        layoutParams = LayoutParams(0, LayoutParams.WRAP_CONTENT).apply {
            topToTop = parentId
            startToStart = parentId
            endToEnd = parentId
            bottomToTop = startButtonId
        }
        text = "00:00"
        id = textViewId
    }

    val startButton = Button(mainActivity).apply {
        layoutParams = LayoutParams(0, LayoutParams.WRAP_CONTENT).apply {
            topToBottom = textViewId
            startToStart = parentId
            endToStart = resetButtonId
            horizontalChainStyle = CHAIN_PACKED
        }
        text = "Start"
        id = startButtonId
    }

    val resetButton = Button(mainActivity).apply {
        layoutParams = LayoutParams(0, LayoutParams.WRAP_CONTENT).apply {
            topToBottom = textViewId
            startToEnd = startButtonId
            endToEnd = parentId
        }
        text = "Reset"
        id = resetButtonId
    }

    val settingsButton = Button(mainActivity).apply {
        layoutParams = LayoutParams(0, LayoutParams.WRAP_CONTENT).apply {
            topToBottom = startButtonId
            startToStart = parentId
            endToEnd = parentId
        }
        text = "Settings"
        id = settingsButtonId
    }

    val progressBar = CustomProgressBar(mainActivity).apply {
        layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
            topToTop = parentId
            startToStart = parentId
            endToEnd = parentId
            bottomToBottom = parentId
        }
        visibility = GONE
        id = progressBarId
    }

    init {
        this.id = parentId
        this.setPadding(10.dpsToPixels(resources.displayMetrics))
        this.addView(startButton)
        this.addView(resetButton)
        this.addView(settingsButton)
        this.addView(textView)
        this.addView(progressBar)
    }


}