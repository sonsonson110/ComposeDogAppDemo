package com.example.kotlindogapp.ui.screens.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlindogapp.data.repository.SettingsRepository
import com.example.kotlindogapp.data.sharepreference.UserPreferences
import com.example.kotlindogapp.notifications.Scheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val alarmScheduler: Scheduler
) :
    ViewModel() {
    private val userPreferencesFlow = settingsRepository.userPreferenceFlow

    val settingUiState =
        userPreferencesFlow.map { userPreferences -> SettingUiState.Shown(userPreferences = userPreferences) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = SettingUiState.Loading
            )

    fun updateUserPreferences(userPreferences: UserPreferences) {
        viewModelScope.launch {
            settingsRepository.updateUserPreference(userPreferences)
        }

    }

    fun setAlarm() {
        viewModelScope.launch {
            userPreferencesFlow.collectLatest { userPreferences ->
//                if (userPreferences.reminderEnabled) {
//                    alarmScheduler.scheduleRemindNotification(
//                        startTime = userPreferences.reminderStartTime,
//                        endTime = userPreferences.reminderEndTime,
//                        numOfRemind = userPreferences.numOfRemind,
//                    )
//                }

                if (userPreferences.dailyPostEnabled) {
                    alarmScheduler.scheduleDailyPost(startTime = userPreferences.reminderStartTime)
                }

            }
        }
    }
}