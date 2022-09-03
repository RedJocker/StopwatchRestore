package org.hyperskill.stopwatch

import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.ConstraintSet.*
import androidx.core.view.setPadding

class MainActivityLayout(private val mainActivity: MainActivity):  ConstraintLayout(mainActivity) {


    val startButton = Button(mainActivity).apply {
        text = "Start"
        id = getId("startButton")
    }

    val resetButton = Button(mainActivity).apply {
        text = "Reset"
        id = getId("resetButton")
    }

    val textView = TextView(mainActivity).apply {
        text = "00:00"
        id = getId("textView")
    }

    init {
        this.id = getId("mainLayout")
        this.setPadding(10)

        val set = ConstraintSet()
        set.clone(this)
        set.connect(textView.id, TOP,    this.id,        TOP,  0)
        set.connect(textView.id, START,  this.id,        START,0)
        set.connect(textView.id, END,    this.id,        END,  0)
        set.connect(textView.id, BOTTOM, startButton.id, TOP,  0)

        set.connect(startButton.id, TOP,   textView.id,    BOTTOM,0)
        set.connect(startButton.id, START, this.id,        START, 0)
        set.connect(startButton.id, END,   resetButton.id, END,   0)


        set.connect(resetButton.id, TOP,   textView.id,    BOTTOM,0)
        set.connect(resetButton.id, START, startButton.id, START, 0)
        set.connect(resetButton.id, END,   this.id,        END,   0)

        this.addView(startButton)
        this.addView(resetButton)
        this.addView(textView)

        set.applyTo(this)
    }


    private fun getId(idString: String): Int {
        return mainActivity.resources.getIdentifier(
                idString, "id", mainActivity.packageName
        )
    }
}