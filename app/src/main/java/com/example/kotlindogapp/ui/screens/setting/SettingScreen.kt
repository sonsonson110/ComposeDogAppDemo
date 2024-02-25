package com.example.kotlindogapp.ui.screens.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kotlindogapp.R
import com.example.kotlindogapp.data.sharepreference.UserPreferences
import com.example.kotlindogapp.data.sharepreference.toTimeString
import com.example.kotlindogapp.ui.common.NumberPicker
import com.example.kotlindogapp.ui.common.TimePickerDialog
import com.example.kotlindogapp.ui.theme.KotlinDogAppTheme

@Composable
fun SettingScreen(onNavigateUp: () -> Unit, viewModel: SettingViewModel = hiltViewModel()) {
    val uiState by viewModel.settingUiState.collectAsState()
    Scaffold(
        topBar = {
            SettingTopBar(
                onNavigateUp = onNavigateUp, applyRemindSettings = viewModel::setAlarm
            )
        },
        content = { paddingValues ->
            SettingContent(
                modifier = Modifier.padding(paddingValues),
                settingUiState = uiState,
                onUserPreferencesChange = viewModel::updateUserPreferences,
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingTopBar(
    onNavigateUp: () -> Unit,
    applyRemindSettings: () -> Unit
) {
    TopAppBar(
        title = { Text(text = "Settings") },
        navigationIcon = {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "navigate back",
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        onNavigateUp()
                        applyRemindSettings()
                    }
            )
        })
}

@Composable
fun SettingContent(
    modifier: Modifier,
    settingUiState: SettingUiState,
    onUserPreferencesChange: (UserPreferences) -> Unit
) {
    if (settingUiState is SettingUiState.Loading) {
        CircularProgressIndicator()
    }

    if (settingUiState is SettingUiState.Shown) {
        Column(modifier = modifier) {
            Card(
                modifier = Modifier.padding(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_alarm_24),
                        contentDescription = null,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(text = "Remind dogs", modifier = Modifier.weight(1f))
                    Checkbox(
                        checked = settingUiState.userPreferences.reminderEnabled,
                        onCheckedChange = { bool ->
                            onUserPreferencesChange(
                                settingUiState.userPreferences.copy(reminderEnabled = bool)
                            )
                        })
                }

                Divider()

                if (!settingUiState.userPreferences.reminderEnabled) return@Card

                // Start time row
                var showStartTimeDialog by remember { mutableStateOf(false) }

                if (showStartTimeDialog) {
                    TimePickerDialog(
                        initialHour = settingUiState.userPreferences.reminderStartTime.hour,
                        initialMinute = settingUiState.userPreferences.reminderStartTime.minute,
                        onDismiss = { showStartTimeDialog = false },
                        onConfirm = { localTime ->
                            onUserPreferencesChange(
                                settingUiState.userPreferences.copy(
                                    reminderStartTime = localTime
                                )
                            )
                            showStartTimeDialog = false
                        },
                        upperBound = settingUiState.userPreferences.reminderEndTime
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { showStartTimeDialog = true },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Start time")
                    Text(
                        text = settingUiState.userPreferences.reminderStartTime.toTimeString(),
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.End
                    )
                }

                Divider()
                // End time row
                var showEndTimeDialog by remember { mutableStateOf(false) }

                if (showEndTimeDialog) {
                    TimePickerDialog(
                        initialHour = settingUiState.userPreferences.reminderEndTime?.hour,
                        initialMinute = settingUiState.userPreferences.reminderEndTime?.minute,
                        onDismiss = { showEndTimeDialog = false },
                        onConfirm = { localTime ->
                            onUserPreferencesChange(
                                settingUiState.userPreferences.copy(
                                    reminderEndTime = localTime
                                )
                            )
                            showEndTimeDialog = false
                        },
                        lowerBound = settingUiState.userPreferences.reminderStartTime
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { showEndTimeDialog = true },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "End time")
                    Text(
                        text = settingUiState.userPreferences.reminderEndTime.toTimeString(),
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.End
                    )
                }
                Divider()

                // Number of remind section
                var showNumberDialog by remember {
                    mutableStateOf(false)
                }

                if (showNumberDialog) {
                    NumberPicker(
                        initialValue = settingUiState.userPreferences.numOfRemind,
                        onDismiss = { showNumberDialog = false },
                        onConfirm = { newNumber ->
                            onUserPreferencesChange(
                                settingUiState.userPreferences.copy(
                                    numOfRemind = newNumber
                                )
                            )
                        }
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { showNumberDialog = true },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Number of remind a day")
                    Text(
                        text = settingUiState.userPreferences.numOfRemind.toString(),
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.End
                    )
                }

                Row {
                    Text(text = "Also notify an interesting doggo", modifier.weight(1f))
                    Checkbox(
                        checked = settingUiState.userPreferences.dailyPostEnabled,
                        onCheckedChange = { bool ->
                            onUserPreferencesChange(
                                settingUiState.userPreferences.copy(dailyPostEnabled = bool)
                            )
                        })
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewSettingScreen() {
    KotlinDogAppTheme {
        SettingScreen(onNavigateUp = {})
    }
}