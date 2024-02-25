package com.example.kotlindogapp.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.example.kotlindogapp.MainActivity
import com.example.kotlindogapp.R
import com.example.kotlindogapp.common.network.ApiState
import com.example.kotlindogapp.data.repository.DogRepository
import com.example.kotlindogapp.model.Dog
import com.example.kotlindogapp.nav.Screen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PostReceiver : BroadcastReceiver() {
    @Inject
    lateinit var dogRepository: DogRepository
    private val TAG = "PostReceiver"

    companion object {
        const val NOTIFICATION_REQUEST_CODE = 9
        const val NOTIFICATION_ID = 9
        const val NOTIFICATION_CHANNEL_ID = "daily_post"
    }

    override fun onReceive(context: Context, intent: Intent) {
        context.ensureNotificationChannelExist(NOTIFICATION_CHANNEL_ID, "daily_dog")
        CoroutineScope(Dispatchers.IO).launch {
            dogRepository.getRemoteRandomDog().collect { apiState ->
                val dog = (apiState as ApiState.Success<*>).data as Dog
                val postNotification = context.createNotification(NOTIFICATION_CHANNEL_ID) {
                    setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("Kotlin Dog App")
                        .setContentText("Check out this ${dog.breeds[0].name}")
                        .setGroup(NOTIFICATION_GROUP)
                        .setAutoCancel(true)
                        .setContentIntent(
                            context.pendingDailyPostIntent(
                                NOTIFICATION_REQUEST_CODE,
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("${Screen.DogDetail.uri}/${dog.id}"),
                                    context,
                                    MainActivity::class.java
                                )
                            )
                        )
                }
                (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(
                    NOTIFICATION_ID,
                    postNotification
                )
                Log.d(TAG, "Post received at ${System.currentTimeMillis()}")
            }

        }
    }
}

fun Context.pendingDailyPostIntent(requestCode: Int, intent: Intent): PendingIntent? =
    TaskStackBuilder.create(this).run {
        addNextIntentWithParentStack(intent)
        getPendingIntent(requestCode, PendingIntent.FLAG_UPDATE_CURRENT)
    }