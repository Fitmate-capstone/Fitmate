package com.tegar.fitmate.ui.screens.detailworkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

@Composable
fun DetailWorkoutScreen(
    workoutId: Long,
    navigateBack: () -> Unit,

    ) {

    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
        Text("Home screen")
    }
}