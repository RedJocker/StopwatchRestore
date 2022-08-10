package org.hyperskill.stopwatch

import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    private lateinit var mStart:Button
    private lateinit var mReset:Button
    private lateinit var mText: TextView
    private val handle = Handler()
    //private var baseTime = SystemClock.elapsedRealtime()
    private var isStart = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mStart = findViewById(R.id.startButton)
        mReset = findViewById(R.id.resetButton)
        mText = findViewById(R.id.textView)

        mStart.setOnClickListener {
            if (!isStart) {
                runnable.baseTime = System.currentTimeMillis()
                handle.postDelayed(runnable, 1000)
                isStart = true
            }

        }

        mReset.setOnClickListener {
            isStart = false
            handle.removeCallbacks(runnable)
            mText.text = "00:00"
        }

    }


     private val runnable = object : Runnable {
         var baseTime = 0L
         override fun run() {
                 //val time = (SystemClock.elapsedRealtime() - baseTime)/ 1000

                 val time = (System.currentTimeMillis() - baseTime) / 1000
                 val mm: String = DecimalFormat("00").format(time % 3600 / 60)
                 val ss: String = DecimalFormat("00").format(time % 60)
                 mText.text = "$mm:$ss"
                 handle.postDelayed(this, 1000)

         }

     }
}