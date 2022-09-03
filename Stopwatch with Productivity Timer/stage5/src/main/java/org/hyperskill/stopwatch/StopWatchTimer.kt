package org.hyperskill.stopwatch

import android.os.CountDownTimer
import android.os.Handler
import java.util.*

class StopWatchTimer(private val handler: Handler, private val onTick: (secondsElapsed: Long) -> Unit) {

//    private var isRunning = false
//    private var baseTime = 0L
//
//    fun start() {
//        if(!isRunning) {
//            baseTime = System.currentTimeMillis()
//            isRunning = true
//            handler.post(::loop)
//        }
//    }
//
//    fun reset() {
//        isRunning = false
//        handler.removeCallbacks(::loop)
//    }
//
//    private fun loop() {
//        val currentTime = System.currentTimeMillis()
//        val elapsed = (currentTime - baseTime) / 1000
//        if(isRunning) {
//            onTick(elapsed)
//            handler.postDelayed(::loop, 1000)
//        }
//    }

    //////////////////////////////////////

    var counter: CountDownTimer? = null

    fun start() {
        if(counter == null) {
            counter = counterFactory()
            counter?.start()
        }
    }

    fun reset() {
        counter?.cancel()
    }

    private fun counterFactory() : CountDownTimer =
        object : CountDownTimer(Long.MAX_VALUE, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                this@StopWatchTimer.onTick((Long.MAX_VALUE - millisUntilFinished) / 1000)
            }

            override fun onFinish() {

            }
        }

    //////////////////////////////////////////////////

//
//    var timer: Timer? = null
//
//    fun start() {
//        if(timer == null) {
//            timer = Timer()
//            timer?.scheduleAtFixedRate(createdNewTimerTask(), 0L, 1000L)
//        }
//    }
//
//    fun reset() {
//        timer?.cancel()
//        timer = null
//    }
//
//    private fun createdNewTimerTask(): TimerTask {
//        return object : TimerTask() {
//
//            val startTime = System.currentTimeMillis()
//
//            override fun run() {
//
//                val currentTime = System.currentTimeMillis()
//                val elapsed = (currentTime - startTime) / 1000
//
//                onTick(elapsed)
//            }
//        }
//    }
}