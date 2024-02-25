package com.example.kotlindogapp.ui.screens.setting

import com.example.kotlindogapp.data.sharepreference.UserPreferences

sealed interface SettingUiState {
    data object Loading : SettingUiState
    data class Shown(
        val userPreferences: UserPreferences
    ): SettingUiState
}