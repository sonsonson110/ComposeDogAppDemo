package com.example.kotlindogapp.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    initialHour: Int?,
    initialMinute: Int?,
    onDismiss: () -> Unit,
    onConfirm: (LocalTime) -> Unit,
    upperBound: LocalTime? = null,
    lowerBound: LocalTime? = null,

) {
    val timeState = rememberTimePickerState(initialHour = initialHour?:0, initialMinute = initialMinute?:0, is24Hour = true)
    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .background(Color.Black)
                .padding(top = 28.dp, start = 20.dp, end = 20.dp, bottom = 12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TimePicker(state = timeState)
            Row(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth(), horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onDismiss) {
                    Text(text = "Dismiss")
                }
                TextButton(
                    onClick = { onConfirm(LocalTime.of(timeState.hour, timeState.minute)) },
                    enabled = timeState.isValid(lowerBound, upperBound)) {
                    Text(text = "Confirm")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
fun TimePickerState.isValid(lowerBound: LocalTime? = null, upperBound: LocalTime? = null): Boolean {
    val currentTime = LocalTime.of(this.hour, this.minute)
    if (lowerBound != null) {
        return currentTime.isAfter(lowerBound)
    }
    else if (upperBound != null) {
        return currentTime.isBefore(upperBound)
    }
    return true
}
