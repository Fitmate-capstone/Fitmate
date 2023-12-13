package com.tegar.fitmate.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tegar.fitmate.R
import com.tegar.fitmate.data.local.entity.SchenduleExerciseEntity
import com.tegar.fitmate.data.model.Exercise
import com.tegar.fitmate.ui.theme.lightblue60
import com.tegar.fitmate.ui.theme.neutral10
import com.tegar.fitmate.ui.theme.neutral80

@Composable
fun ExerciseItemSchedule(exercise: SchenduleExerciseEntity, navigateToDetailSchedule: (Long) -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = neutral80
        ),
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
            .clickable {
                navigateToDetailSchedule(exercise.id_exercise)
            },
    ) {
        Row(

        ) {
            Box(
                modifier = Modifier
                    .background(
                        neutral10,
                    )
                    .width(64.dp)
                    .fillMaxHeight()

            ) {
                // image
                GifImage(70.dp,exercise.exercise_gif_url)
            }
            Column(
                verticalArrangement = Arrangement.SpaceBetween,

                modifier = Modifier.padding(10.dp)
            ) {


                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        exercise.name_exercise,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = neutral10,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            ),
                        overflow = TextOverflow.Ellipsis
                    )

                    if (exercise.isFinished) {
                        Text(
                            "Done",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = neutral10,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,

                                ),
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(26.dp)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(
                            9.dp
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Filled.LocalFireDepartment,

                            contentDescription = null,
                            tint = lightblue60
                        )
                        Text(
                            text = stringResource(
                                R.string.calori_format,
                                exercise.exercise_calori.toInt()
                            ),
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = neutral10
                            )
                        )
                    }
                }
            }

        }
    }
}