package com.example.kotlindogapp.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.kotlindogapp.data.repository.DogRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalTime
import java.util.Calendar
import javax.inject.Inject


class AlarmScheduler @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dogRepository: DogRepository,
) : Scheduler {
    private val TAG = "AlarmScheduler"
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    override fun scheduleDailyPost(startTime: LocalTime): Unit = with(context) {
        val dailyPostPendingIntent = Intent(this, PostReceiver::class.java).let {
            PendingIntent.getBroadcast(
                this,
                PostReceiver.NOTIFICATION_ID,
                it,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        val postCalendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, startTime.hour)
            set(Calendar.MINUTE, startTime.minute)
        }

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            postCalendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            dailyPostPendingIntent
        )
        Log.d(
            TAG,
            "Post time set: ${postCalendar.get(Calendar.HOUR_OF_DAY)}:${postCalendar.get(Calendar.MINUTE)}"
        )
    }

    override fun scheduleRemindNotification(
        startTime: LocalTime,
        endTime: LocalTime,
        numOfRemind: Int
    ): Unit = with(context) {
        val remindPendingIntent = Intent(this, RemindReceiver::class.java).let {
            PendingIntent.getBroadcast(
                this,
                RemindReceiver.NOTIFICATION_ID,
                it,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        val reminders = createReminders(startTime, endTime, numOfRemind)
        for (reminder in reminders) {
            // check if  reminder time in the pass
            var startMillis = reminder.timeInMillis
//            if (reminder.timeInMillis <= Calendar.getInstance().timeInMillis) {
//                startMillis += (AlarmManager.INTERVAL_DAY + 1)
//            }
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                startMillis,
                AlarmManager.INTERVAL_DAY,
                remindPendingIntent
            )
            Log.d(
                TAG,
                "Remind time set: ${reminder.get(Calendar.HOUR_OF_DAY)}:${reminder.get(Calendar.MINUTE)}"
            )
        }

    }

    private fun createReminders(
        startTime: LocalTime,
        endTime: LocalTime,
        numOfRemind: Int
    ): List<Calendar> {
        val reminders = arrayListOf<Calendar>()

        // Chuyển đổi LocalTime sang giờ phút
        val startMinutes = startTime.hour * 60 + startTime.minute
        val endMinutes = endTime.hour * 60 + endTime.minute

        // Tính số phút giữa reminderStartTime và reminderEndTime
        val totalMinutes = endMinutes - startMinutes

        // Khoảng cách mỗi lần nhắc nhở tính từ reminderStartTime
        val reminderInterval = totalMinutes / numOfRemind

        // Tạo các thời điểm nhắc nhở
        for (i in 0 until numOfRemind) {
            val reminderMinutes = startMinutes + i * reminderInterval
            val reminderHour = reminderMinutes / 60
            val reminderMinute = reminderMinutes % 60

            // Tạo Calendar object và đặt thời gian
            val reminderCalendar: Calendar = Calendar.getInstance()
            reminderCalendar.set(Calendar.HOUR_OF_DAY, reminderHour)
            reminderCalendar.set(Calendar.MINUTE, reminderMinute)
            reminders.add(reminderCalendar)
        }

        return reminders
    }
}