package com.example.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core_ui.theme.Black
import com.example.core_ui.theme.White

@Composable
fun DateTimeInfo(
    date: String,
    time: String
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .background(
                color = Black.copy(alpha = 0.6f),
                shape = MaterialTheme.shapes.small
            )
            .padding(horizontal = 6.dp, vertical = 4.dp)
    ) {
        Text(
            text = date,
            color = White,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = time,
            color = White,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DateTimeInfoPreview() {
    DateTimeInfo(
        date = "2023.05.15",
        time = "14:30"
    )
}