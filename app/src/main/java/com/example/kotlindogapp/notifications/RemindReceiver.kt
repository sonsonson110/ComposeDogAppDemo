package com.example.kotlindogapp.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.kotlindogapp.R

const val TARGET_ACTIVITY_NAME = "com.example.kotlindogapp.MainActivity"
const val NOTIFICATION_GROUP = "NOTIFICATIONS"

class RemindReceiver : BroadcastReceiver() {
    private val TAG = "RemindReceiver"

    companion object {
        const val NOTIFICATION_REQUEST_CODE = 8
        const val NOTIFICATION_ID = 8
        const val NOTIFICATION_CHANNEL_ID = "daily_reminder"
    }

    override fun onReceive(context: Context, intent: Intent) {
        context.ensureNotificationChannelExist(NOTIFICATION_CHANNEL_ID, "Dogs reminder")

        val reminderNotification = context.createNotification(NOTIFICATION_CHANNEL_ID) {
            setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Kotlin Dog App")
                .setContentText("Don't forget to heal yourself with doggos")
                .setGroup(NOTIFICATION_GROUP)
                .setAutoCancel(true)
                .setContentIntent(context.pendingRemindIntent(NOTIFICATION_REQUEST_CODE, Intent().apply {
                    action = Intent.ACTION_VIEW
                    component = ComponentName(
                        context.packageName,
                        TARGET_ACTIVITY_NAME,
                    )
                }))
        }
        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(
            NOTIFICATION_ID,
            reminderNotification
        )
        Log.d(TAG, "Remind received at ${System.currentTimeMillis()}")
    }
}

// Create reminder notification
fun Context.createNotification(
    channelId: String,
    block: NotificationCompat.Builder.() -> Unit,
): Notification {
    return NotificationCompat.Builder(
        this,
        channelId,
    )
        .setDefaults(NotificationCompat.DEFAULT_SOUND)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .apply(block)
        .build()
}

// Ensure the notification channel is existed
fun Context.ensureNotificationChannelExist(channelId: String, name: String) {
    val channel = NotificationChannel(
        channelId,
        name,
        NotificationManager.IMPORTANCE_DEFAULT
    )
    channel.description = "Remind you to browse daily dogs"
    (this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(
        channel
    )
}

fun Context.pendingRemindIntent(requestCode: Int, intent: Intent): PendingIntent? =
    PendingIntent.getActivity(
        this,
        requestCode,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
    )