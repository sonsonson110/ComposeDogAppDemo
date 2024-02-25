package com.example.kotlindogapp.notifications

import java.time.LocalTime

interface Scheduler {
    fun scheduleDailyPost(startTime: LocalTime)
    fun scheduleRemindNotification(startTime: LocalTime, endTime: LocalTime, numOfRemind: Int): Unit
}