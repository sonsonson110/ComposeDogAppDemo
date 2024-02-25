package com.example.kotlindogapp.di

import com.example.kotlindogapp.notifications.AlarmScheduler
import com.example.kotlindogapp.notifications.Scheduler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AlarmModule {
    @Binds
    @Singleton
    abstract fun bindAlarmSchedule(
        alarmScheduler: AlarmScheduler
    ): Scheduler
}