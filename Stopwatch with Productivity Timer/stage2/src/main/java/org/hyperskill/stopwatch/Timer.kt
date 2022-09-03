package org.hyperskill.stopwatch

import android.os.Handler

class Timer(private val handler: Handler, private val onTick: (secondsElapsed: Long) -> Unit) {

    private var isRunning = false
    private var baseTime = 0L

    fun start() {
        if(!isRunning) {
            baseTime = System.currentTimeMillis()
            isRunning = true
            handler.post(::loop)
        }
    }

    fun reset() {
        isRunning = false
        handler.removeCallbacks(::loop)
    }

    private fun loop() {
        val currentTime = System.currentTimeMillis()
        val elapsed = (currentTime - baseTime) / 1000
        if(isRunning) {
            onTick(elapsed)
            handler.postDelayed(::loop, 1000)
        }
    }
}