package org.hyperskill.tests.stopwatch

import android.app.AlertDialog
import android.graphics.Color
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import org.hyperskill.stopwatch.MainActivity
import org.hyperskill.tests.stopwatch.internals.StopwatchUnitTest
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowAlertDialog
import java.util.concurrent.TimeUnit

// Version 2.0
@Config(instrumentedPackages = ["org.hyperskill.stopwatch"])
@RunWith(RobolectricTestRunner::class)
class Stage4UnitTest : StopwatchUnitTest<MainActivity>(MainActivity::class.java) {


    private val startButton: Button by lazy {
        val view = activity.findViewByString<Button>("startButton")

        val message = "For view with id \"startButton\", in property \"text\""
        assertEquals(message, "start", view.text.toString().lowercase())

        view
    }

    private val resetButton: Button by lazy {
        val view = activity.findViewByString<Button>("resetButton")

        val message = "For view with id \"resetButton\", in property \"text\""
        assertEquals(message, "reset", view.text.toString().lowercase())

        view
    }

    private val settingsButton: Button by lazy {
        val view = activity.findViewByString<Button>("settingsButton")

        val message = "For view with id \"settingsButton\", in property \"text\""
        assertEquals(message, "settings", view.text.toString().lowercase())

        view
    }

    private val textView: TextView by lazy {
        activity.findViewByString("textView")
    }


    private val messageDialogNotFound = "Is dialog shown when \"settingsButton\" is clicked?"


    @Test
    fun testShouldCheckSettingsButtonExist() {
        testActivity {
            settingsButton
        }
    }

    @Test
    fun testShouldCheckSettingsButtonEnable() {
        testActivity {
            val message1 = "view with id \"settingsButton\" should be enabled when timer is stopped"
            assertTrue(message1, settingsButton.isEnabled)

            startButton.clickAndRun()

            val message2 = "view with id \"settingsButton\" should be disabled when timer is running"
            assertFalse(message2, settingsButton.isEnabled)

            resetButton.clickAndRun()
            assertTrue(message1, settingsButton.isEnabled)
        }
    }

    @Test
    fun testShouldShowAlertDialogOnSettingsButtonClick() {
        testActivity {
            settingsButton.clickAndRun()
            val dialog = ShadowAlertDialog.getLatestAlertDialog()
            assertNotNull(messageDialogNotFound, dialog)
        }
    }

    @Test
    fun testDialogButtonsShouldContainText() {
        testActivity {
            val expectedOk = "OK"
            val expectedCancel = "Cancel"

            settingsButton.clickAndRun()
            val dialog = ShadowAlertDialog.getLatestAlertDialog()
            assertNotNull(messageDialogNotFound, dialog)

            val actualOk = dialog.getButton(AlertDialog.BUTTON_POSITIVE).text
            assertEquals("positive button contains wrong text", expectedOk, actualOk)

            val actualCancel = dialog.getButton(AlertDialog.BUTTON_NEGATIVE).text
            assertEquals("negative button contains wrong text", expectedCancel, actualCancel)
        }

    }

    @Test
    fun testShouldCheckLimitIsNotSetOnCancel() {
        testActivity {
            val secondsToCount = 2

            settingsButton.clickAndRun()
            val dialog = ShadowAlertDialog.getLatestAlertDialog()
            assertNotNull(messageDialogNotFound, dialog)

            val upperLimitEditText = dialog.findViewByString<EditText>("upperLimitEditText")
            upperLimitEditText.setText("$secondsToCount")

            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).clickAndRun()
            val sleepTime = secondsToCount * 1000 + 1300L

            startButton.clickAndRun(sleepTime)

            val message = "\"textView\" color should not be RED if limit has not been set"
            val actual = textView.currentTextColor
            assertNotEquals(message, Color.RED, actual)
        }

    }

    @Test
    fun testShouldCheckLimitIsSetOnOk() {
        testActivity {
            val secondsToCount = 2L

            settingsButton.clickAndRun()
            val dialog = ShadowAlertDialog.getLatestAlertDialog()
            assertNotNull(messageDialogNotFound, dialog)

            dialog.findViewByString<EditText>("upperLimitEditText").setText("$secondsToCount")
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).clickAndRun()

            startButton.clickAndRun()

            val messageBefore = "\"textView\" color should not be RED before limit is reached"
            val actualBefore = textView.currentTextColor
            assertNotEquals(messageBefore, Color.RED, actualBefore)

            val sleepTime = secondsToCount * 1000L + 1000L
            shadowLooper.idleFor(sleepTime, TimeUnit.MILLISECONDS)

            val messageAfter = "\"textView\" color should be RED after limit is reached"
            val actualAfter = textView.currentTextColor
            assertEquals(messageAfter, Color.RED, actualAfter)
        }

    }

    @Test
    fun testShouldCheckColorsOnRestart() {
        testActivity {
            val secondsToCount = 2L

            settingsButton.clickAndRun()
            val dialog = ShadowAlertDialog.getLatestAlertDialog()
            assertNotNull(messageDialogNotFound, dialog)

            dialog.findViewByString<EditText>("upperLimitEditText").setText("$secondsToCount")
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).clickAndRun()

            val timeToSleep = secondsToCount * 1000L + 1300L
            startButton.clickAndRun(timeToSleep)

            val messageAfter = "\"textView\" color should be RED after limit is reached"
            val actualAfter = textView.currentTextColor
            assertEquals(messageAfter, Color.RED, actualAfter)

            resetButton.clickAndRun()
            startButton.clickAndRun()

            val messageBefore = "\"textView\" color should not be RED before limit is reached"
            val actualBefore = textView.currentTextColor
            assertNotEquals(messageBefore, Color.RED, actualBefore)
        }
    }
}