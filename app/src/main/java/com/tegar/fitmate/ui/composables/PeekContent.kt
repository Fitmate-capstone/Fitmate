package com.tegar.fitmate.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tegar.fitmate.R
import com.tegar.fitmate.data.local.faker.FakeData
import com.tegar.fitmate.data.remote.model.PredictionData


@Composable
fun PeekContent(
    predictionResult : PredictionData,
    navigateToDetail: (workoutId: Long) -> Unit,

    ) {
    Column(
        Modifier.padding(16.dp)
    ) {
        predictionResult.gymEquipmentPrediction?.let {
            Text(
                it, style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Text(
            "Here are some exercises you can do.",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        predictionResult.exercise?.data?.let {
            ExerciseItem(
                exercise = it,
                navigateToDetailSchedule = {
                    navigateToDetail(it)
                }
            )
        }


    }
}