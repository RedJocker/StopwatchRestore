package org.hyperskill.stopwatch

import android.text.InputType
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.setPadding
import org.hyperskill.stopwatch.Utils.dpsToPixels
import org.hyperskill.stopwatch.Utils.resId


class SettingsDialogLayout(private val mainActivity: MainActivity) : LinearLayout(mainActivity) {


    private val settingsTextViewId = mainActivity.resId("settingsTextView")
    private val upperLimitEditTextId = mainActivity.resId("upperLimitEditText")

    private val settingsTextView  = TextView(mainActivity).apply {
        layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        text = "Set upper limit in seconds"
        id = settingsTextViewId
    }

    val upperLimitEditText = EditText(mainActivity).apply {
        layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        inputType = InputType.TYPE_CLASS_NUMBER
        id = upperLimitEditTextId
    }

    init {
        orientation = VERTICAL
        setPadding(15.dpsToPixels(mainActivity.resources.displayMetrics))
        addView(settingsTextView)
        addView(upperLimitEditText)
    }
}