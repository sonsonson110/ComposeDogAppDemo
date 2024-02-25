package com.example.kotlindogapp.data.sharepreference

import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class UserPreferences(
    val reminderEnabled: Boolean,
    val reminderStartTime: LocalTime,
    val reminderEndTime: LocalTime,
    val numOfRemind: Int,

    val dailyPostEnabled: Boolean,
)

fun LocalTime.toTimeString(): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return this.format(formatter)
}