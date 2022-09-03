package org.hyperskill.stopwatch

import android.annotation.SuppressLint
import android.app.*
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat


object MainActivityServices {
    fun MainActivity.showSettingsDialogGetLimitTime(resultCallback : (Int) -> Unit)  {
        val layout = SettingsDialogLayout(this)
        AlertDialog.Builder(this)
                .setView(layout)
                .setNegativeButton("Cancel")  { dialog, which ->
                    dialog.dismiss()
                    resultCallback(0)
                }.setPositiveButton("OK") { dialog, which ->
                    val textInput = layout.upperLimitEditText.text.toString()
                    val numberInput = textInput.toIntOrNull() ?: 0
                    resultCallback(numberInput)
                    dialog.dismiss()
                }.show()
    }


    @SuppressLint("NewApi")
    fun Activity.sendTimeLimitExceededNotification(timeLimit: String) {

        val channel = NotificationChannel("org.hyperskill", "Stopwatch", NotificationManager.IMPORTANCE_HIGH)

        val notification = NotificationCompat.Builder(this, "org.hyperskill")
                .setContentTitle("Stopwatch notification")
                .setContentText("Time limit exceeded: $timeLimit")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setOnlyAlertOnce(true)
                .build()

        notification.flags = notification.flags.or(Notification.FLAG_INSISTENT);

        ContextCompat.getSystemService(this, NotificationManager::class.java) ?.also {
            it.createNotificationChannel(channel)
            it.notify(393939, notification)
        }
    }
}