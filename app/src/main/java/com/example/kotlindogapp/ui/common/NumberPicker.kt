package com.example.kotlindogapp.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kotlindogapp.ui.theme.Typography

@Composable
fun NumberPicker(
    initialValue: Int,
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit,
) {
    var number by remember { mutableIntStateOf(initialValue) }
    AlertDialog(
        title = { Text(text = "Choose remind times per day", style = Typography.titleMedium) },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onConfirm(number)
                onDismiss()
            }) { Text("Confirm") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Dismiss") } },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(400.dp),
        text = {
            LazyColumn(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                for (i in 1..10) {
                    item {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = number == i,
                                onClick = { number = i })
                            Spacer(modifier = Modifier.size(8.dp))
                            Text(text = "$i times")
                        }
                    }
                }
            }
        }
    )
}