package org.hyperskill.tests.stopwatch

import android.widget.Button
import android.widget.TextView
import org.hyperskill.stopwatch.MainActivity
import org.hyperskill.tests.stopwatch.internals.StopwatchUnitTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

// Version 2.0
@Config(instrumentedPackages = ["org.hyperskill.stopwatch"])
@RunWith(RobolectricTestRunner::class)
class Stage2UnitTest : StopwatchUnitTest<MainActivity>(MainActivity::class.java) {


    private val startButton: Button by lazy {
        val view = activity.findViewByString<Button>("startButton")

        val message = "in button property \"text\""
        assertEquals(message, "Start", view.text)

        view
    }

    private val resetButton: Button by lazy {
        val view = activity.findViewByString<Button>("resetButton")

        val message = "in button property \"text\""
        assertEquals(message, "Reset", view.text)

        view
    }

    private val textView: TextView by lazy {
        activity.findViewByString("textView")
    }

    private val messageTextViewAssertionError = "in TextView property \"text\""

    @Test
    fun checkCheckTimerInitialValue() {
        testActivity {
            val expected = "00:00"
            val actual = textView.text
            assertEquals(messageTextViewAssertionError, expected, actual)
        }
    }

    @Test
    fun checkTakeOneSecondToCountOneSecondOnStartButtonClick() {
        testActivity {
            val expected = "00:00"

            startButton.clickAndRun(300)

            val actual = textView.text
            assertEquals(messageTextViewAssertionError, expected, actual)
        }
    }

    @Test
    fun checkCountOneSecondAfterOneSecondOnStartButtonClick() {
        testActivity {
            val expected = "00:01"

            startButton.clickAndRun(1100)

            val actual = textView.text
            assertEquals(messageTextViewAssertionError, expected, actual)
        }
    }


    @Test
    fun checkStopTimerAndResetCountOnResetButtonClick() {
        testActivity {
            val expected = "00:00"

            startButton.clickAndRun(1100)
            resetButton.clickAndRun(200)

            val actual = textView.text
            assertEquals(messageTextViewAssertionError, expected, actual)
        }
    }

    @Test
    fun checkContinueCountOnPressingStartButtonAgain() {
        testActivity {
            val expected = "00:11"
            startButton.clickAndRun(1100)

            startButton.performClick()
            startButton.performClick()
            startButton.clickAndRun(10_100)

            val actual = textView.text
            assertEquals(messageTextViewAssertionError, expected, actual)
        }
    }

    @Test
    fun checkIgnorePressingResetButtonAgain() {
        testActivity {
            val expected = "00:00"

            startButton.clickAndRun(10_000)
            resetButton.clickAndRun(10_000)
            resetButton.clickAndRun(10_000)

            val actual = textView.text
            assertEquals(messageTextViewAssertionError, expected, actual)
        }
    }

    @Test
    fun checkDisplayOneMinuteInsteadOf60Seconds() {
        testActivity {
            val expected = "01:00"

            startButton.clickAndRun(60_100)

            val actual = textView.text
            assertEquals(messageTextViewAssertionError, expected, actual)
        }
    }

    @Test
    fun checkMinuteIsNotEarly() {
        testActivity {
            val expected = "01:59"

            startButton.clickAndRun(119_100)

            val actual = textView.text
            assertEquals(messageTextViewAssertionError, expected, actual)
        }
    }
}