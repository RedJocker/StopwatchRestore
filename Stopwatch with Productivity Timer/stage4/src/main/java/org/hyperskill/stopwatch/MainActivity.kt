package org.hyperskill.stopwatch

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import kotlin.math.floor

@SuppressLint("NewApi")
class MainActivity : AppCompatActivity() {

    var timerValue: Int = 0
    var timerLimit: Int = 0
    private var isCounterRunning : Boolean = false
    private val timerHandler = Handler(Looper.getMainLooper())
    private val loaderHandler = Handler(Looper.getMainLooper())
    private val colors = arrayOf(Color.GREEN, Color.RED, Color.BLUE)
    private var color = colors[0]

    private val updateCount: Runnable = object : Runnable {
        override fun run() {
            isCounterRunning = true
            timerValue++
            updateTime(prepString(timerValue))

            if (timerLimit in 1 until timerValue) {
                updateColorText()
            }
            timerHandler.postDelayed(this, 1000)



        }
    }

    private val updateColor: Runnable = object : Runnable {

        override fun run() {
            val bar = findViewById<ProgressBar>(R.id.progressBar)
            color = colors[(colors.indexOf(color) + 1) % colors.size]
            bar.indeterminateTintList = ColorStateList.valueOf(color);
            loaderHandler.postDelayed(this, 1000)

        }

    }
    private fun updateColorText() {
        val texView  = findViewById<TextView>(R.id.textView)
        texView.setTextColor(Color.RED)
    }

    private fun showLoader() {
        val bar = findViewById<ProgressBar>(R.id.progressBar)
        bar.indeterminateTintList = ColorStateList.valueOf(color);
        bar.visibility = View.VISIBLE
        updateColor.run()
    }

    private fun prepString(sec: Int): String {
        var minutes: Int = floor((sec / 60).toDouble()).toInt()
        var seconds: Int  = sec % 60;
        var prependMin:String = "";
        var prependSec: String = ""
        if( minutes < 10 ) {
            prependMin = "0";
        }
        if (seconds < 10) {
            prependSec = "0"
        }

        return "$prependMin$minutes:$prependSec$seconds";
    }

    private fun updateTime(newStr: String) {
        val texView  = findViewById<TextView>(R.id.textView)
        texView.text = newStr
    }

    private val startTimer = View.OnClickListener {
        if(!isCounterRunning) {
            timerHandler.postDelayed(updateCount, 1000)
            showLoader()
            findViewById<Button>(R.id.settingsButton).isEnabled = false
        }
    }

    private val resetTimer = View.OnClickListener {
        timerValue = 0
        timerLimit = 0
        timerHandler.removeCallbacks(updateCount)
        updateTime(prepString(timerValue))
        isCounterRunning = false


        val bar = findViewById<ProgressBar>(R.id.progressBar)
        bar.visibility = View.INVISIBLE
        loaderHandler.removeCallbacks(updateColor)
        findViewById<Button>(R.id.settingsButton).isEnabled = true

        val texView  = findViewById<TextView>(R.id.textView)
        texView.setTextColor(Color.BLACK)

    }

    private val setTimer = View.OnClickListener {
        val contentView = LayoutInflater.from(this).inflate(R.layout.dialog_main, null, false)

        AlertDialog.Builder(this)
            .setTitle("Set upper limit in seconds")
            .setView(contentView)
            .setPositiveButton("OK") { _, _ ->
                val editText = contentView.findViewById<EditText>(R.id.upperLimitEditText)
                Log.d("main", editText.toString())
                timerLimit = editText.text.toString().toInt()
            }
            .setNegativeButton("Cancel", null)
            .show()

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.startButton).setOnClickListener(startTimer)
        findViewById<Button>(R.id.resetButton).setOnClickListener(resetTimer)
        findViewById<Button>(R.id.settingsButton).setOnClickListener(setTimer)
    }

}