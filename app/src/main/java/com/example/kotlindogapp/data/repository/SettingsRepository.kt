package com.example.kotlindogapp.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.kotlindogapp.data.sharepreference.UserPreferences
import com.example.kotlindogapp.data.sharepreference.toTimeString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.time.LocalTime
import javax.inject.Inject

class SettingsRepository @Inject constructor(private val dataStore: DataStore<Preferences>) {
    private val TAG: String = "SettingsRepository"

    private object PreferencesKeys {
        val REMINDER_ENABLED = stringPreferencesKey("reminder_enabled")
        val START_TIME = stringPreferencesKey("start_time")
        val END_TIME = stringPreferencesKey("end_time")
        val NUMBER_OF_REMIND = stringPreferencesKey("num_of_remind")

        val DAILY_POST_ENABLED = stringPreferencesKey("daily_post_enabled")
    }

    // Get user preference flow
    val userPreferenceFlow: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences.", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            mapUserPreferences(preferences)
        }

    // Update user preference
    suspend fun updateUserPreference(userPreferences: UserPreferences) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.REMINDER_ENABLED] = userPreferences.reminderEnabled.toString()
            preferences[PreferencesKeys.START_TIME] = userPreferences.reminderStartTime.toTimeString()
            preferences[PreferencesKeys.END_TIME] = userPreferences.reminderEndTime.toTimeString()
            preferences[PreferencesKeys.NUMBER_OF_REMIND] = userPreferences.numOfRemind.toString()

            preferences[PreferencesKeys.DAILY_POST_ENABLED] = userPreferences.dailyPostEnabled.toString()

        }
    }

    private fun mapUserPreferences(preferences: Preferences): UserPreferences {

        val reminderEnabled = preferences[PreferencesKeys.REMINDER_ENABLED]?.toBoolean() ?: false
        val startTime = LocalTime.parse(preferences[PreferencesKeys.START_TIME]?: "00:00")
        val endTime = LocalTime.parse(preferences[PreferencesKeys.END_TIME]?: "00:00")
        val numOfRemind = preferences[PreferencesKeys.NUMBER_OF_REMIND]?.toIntOrNull() ?: 1

        val dailyPostEnabled = preferences[PreferencesKeys.DAILY_POST_ENABLED]?.toBoolean() ?: false

        return UserPreferences(reminderEnabled, startTime, endTime, numOfRemind, dailyPostEnabled)
    }
}