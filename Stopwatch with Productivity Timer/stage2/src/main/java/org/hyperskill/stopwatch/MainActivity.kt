package org.hyperskill.stopwatch

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import org.hyperskill.stopwatch.Utils.timeString

class MainActivity : AppCompatActivity() {

    private lateinit var layout: MainActivityLayout
    private lateinit var timer : Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layout = MainActivityLayout(this)
        layout.startButton.setOnClickListener(::onStartButtonClick)
        layout.resetButton.setOnClickListener(::onResetButtonClick)
        timer = Timer(Handler(Looper.getMainLooper()), ::onTimerTick)
        setContentView(layout)
    }

    private fun onTimerTick(secondsElapsed: Long) {
        runOnUiThread {
            layout.textView.text = secondsElapsed.timeString()
        }
    }

    private fun onStartButtonClick(v: View) {
        timer.start()
    }

    private fun onResetButtonClick(v: View) {
        timer.reset()
        runOnUiThread {
            layout.textView.text = 0L.timeString()
        }
    }
}