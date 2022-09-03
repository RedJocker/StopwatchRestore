package org.hyperskill.stopwatch

import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.CHAIN_PACKED
import androidx.core.view.setPadding
import org.hyperskill.stopwatch.Utils.dpsToPixels

class MainActivityLayout(private val mainActivity: MainActivity):  ConstraintLayout(mainActivity) {

    private val parentId = getId("mainLayout")
    private val textViewId = getId("textView")
    private val resetButtonId = getId("resetButton")
    private val startButtonId = getId("startButtonId")

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

    init {
        this.id = parentId
        this.setPadding(10.dpsToPixels(resources.displayMetrics))
        this.addView(startButton)
        this.addView(resetButton)
        this.addView(textView)
    }

    private fun getId(idString: String): Int {
        return mainActivity.resources.getIdentifier(
                idString, "id", mainActivity.packageName
        )
    }
}