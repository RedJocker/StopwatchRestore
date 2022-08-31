package org.hyperskill.tests.stopwatch

import android.app.AlertDialog
import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.graphics.drawable.Icon
import android.widget.Button
import android.widget.EditText
import org.hyperskill.stopwatch.MainActivity
import org.hyperskill.tests.stopwatch.internals.StopwatchUnitTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowAlertDialog
import java.util.concurrent.TimeUnit

// Version 2.0
@Config(instrumentedPackages = ["org.hyperskill.stopwatch"])
@RunWith(RobolectricTestRunner::class)
class Stage5UnitTest : StopwatchUnitTest<MainActivity>(MainActivity::class.java) {


    private val startButton: Button by lazy {
        val view = activity.findViewByString<Button>("startButton")

        val message = "For view with id \"startButton\", in property \"text\""
        assertEquals(message, "start", view.text.toString().lowercase())

        view
    }

    private val settingsButton: Button by lazy {
        val view = activity.findViewByString<Button>("settingsButton")

        val message = "For view with id \"settingsButton\", in property \"text\""
        assertEquals(message, "settings", view.text.toString().lowercase())

        view
    }

    private val notificationManager by lazy {
        Shadows.shadowOf(
            activity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        )
    }


    @Test
    fun testShouldCheckNotificationVisibilityOnTimeExceed() {
        testActivity {
            val secondsToCount = 10

            settingsButton.clickAndRun()
            val dialog = ShadowAlertDialog.getLatestAlertDialog()
            dialog.findViewByString<EditText>("upperLimitEditText").setText("$secondsToCount")
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).clickAndRun()

            val timeToSleep = secondsToCount * 1000 + 1100L
            startButton.clickAndRun(timeToSleep)

            val notification: Notification? = notificationManager.getNotification(393939)

            val messageNotificationId =
                "Could not find notification with id 393939. Did you set the proper id?"
            assertNotNull(messageNotificationId, notification)
            notification!!

            val messageChannelId = "The notification channel id does not equals \"org.hyperskill\""
            val actualChannelId = notification.channelId
            assertEquals(messageChannelId, "org.hyperskill", actualChannelId)

            val messageIcon = "Have you set the notification smallIcon?"
            val actualIcon: Icon? = notification.smallIcon
            assertNotNull(messageIcon, actualIcon)

            val messageTitle = "Have you set the notification title?"
            val actualTitle = notification.extras.getCharSequence(Notification.EXTRA_TITLE)?.toString()
            assertNotNull(messageTitle, actualTitle)

            val messageContent = "Have you set the notification content?"
            val actualContent = notification.extras.getCharSequence(Notification.EXTRA_TEXT)?.toString()
            assertNotNull(messageContent, actualContent)
        }
    }
}