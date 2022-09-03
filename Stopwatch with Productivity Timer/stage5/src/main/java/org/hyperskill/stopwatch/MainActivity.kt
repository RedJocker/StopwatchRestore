package org.hyperskill.stopwatch

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import org.hyperskill.stopwatch.MainActivityServices.sendTimeLimitExceededNotification
import org.hyperskill.stopwatch.MainActivityServices.showSettingsDialogGetLimitTime
import org.hyperskill.stopwatch.Utils.timeString

class MainActivity : AppCompatActivity() {

    private lateinit var layout: MainActivityLayout
    private lateinit var timer : StopWatchTimer
    var timeLimit = 0
    var notified =  false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layout = MainActivityLayout(this)
        layout.startButton.setOnClickListener(::onStartButtonClick)
        layout.resetButton.setOnClickListener(::onResetButtonClick)
        layout.settingsButton.setOnClickListener(::onSettingsButtonClick)
        timer = StopWatchTimer(Handler(Looper.getMainLooper()), ::onTimerTick)
        setContentView(layout)
    }


    private fun onTimerTick(secondsElapsed: Long) {
        val timeString = secondsElapsed.timeString()

        runOnUiThread {
            layout.textView.text = timeString
            layout.progressBar.nextColor()
            if(timeLimit in 1 until secondsElapsed) {
                layout.textView.setTextColor(Color.RED)
                if(notified.not()) {
                    sendTimeLimitExceededNotification(timeString)
                }
            }
        }
    }

    private fun onStartButtonClick(v: View) {
        timer.start()
        runOnUiThread {
            layout.progressBar.visibility = VISIBLE
            layout.settingsButton.isEnabled = false
        }
    }

    private fun onResetButtonClick(v: View) {
        timer.reset()

        runOnUiThread {
            layout.textView.text = 0L.timeString()
            layout.textView.setTextColor(Color.BLACK)
            layout.progressBar.visibility = GONE
            layout.settingsButton.isEnabled = true

        }
    }

    private fun onSettingsButtonClick(v: View) {
        showSettingsDialogGetLimitTime { timeLimitInput ->
            timeLimit = timeLimitInput
        }
    }
}