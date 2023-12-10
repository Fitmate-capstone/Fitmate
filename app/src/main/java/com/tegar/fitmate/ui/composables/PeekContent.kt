package com.tegar.fitmate.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tegar.fitmate.R


@Composable
fun PeekContent() {
    Column(
        Modifier.padding(16.dp)
    ){
        Text(
            "Cable crossover machine", style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            "Berikut adalah latihan yang bisa anda lakukan Dengan ",
            style = MaterialTheme.typography.bodySmall
        )


        ExerciseItem(
            name = "Bench Press",
            img = R.drawable.bench_press
        )

        ExerciseItem(
            name = "Squat ",
            img = R.drawable.squat
        )
    }
}