package com.tegar.fitmate.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tegar.fitmate.R
import com.tegar.fitmate.data.local.faker.FakeData
import com.tegar.fitmate.data.model.Exercise
import com.tegar.fitmate.ui.theme.FitmateTheme
import com.tegar.fitmate.ui.theme.lightblue60
import com.tegar.fitmate.ui.theme.neutral10
import com.tegar.fitmate.ui.theme.neutral30
import com.tegar.fitmate.ui.theme.neutral80


@Composable
fun ExerciseGridCard(exercise: Exercise, modifier: Modifier = Modifier) {

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = neutral80
        ),

        modifier = modifier
            .width(166.dp)
            .height(197.dp)
    ) {
        Image(
            painter = painterResource(id = exercise.photo),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth().height(104.dp),
        )
        Column(

            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.padding(12.dp),
        ){
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${exercise.category.name} ",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = neutral30
                    )
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    for (i in 1..3) {
                        Icon(
                            imageVector = if (i <= exercise.level) Icons.Default.Bolt else Icons.Outlined.Bolt,
                            contentDescription = null,
                            tint = if (i <= exercise.level) lightblue60 else neutral30,
                            modifier = Modifier.size(19.dp)
                        )
                    }
                }
            }

            Text(
                text = exercise.name,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = neutral10,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(4.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Informasi Tambahan
                ExerciseAdditionInformation(
                    icon = Icons.Filled.LocalFireDepartment,
                    text = stringResource(id = R.string.calori_format, exercise.calEstimation),
                    iconTint = lightblue60
                )

                // Rating
                ExerciseAdditionInformation(
                    icon = Icons.Filled.Star,
                    text = stringResource(id = R.string.rating_format, exercise.rating),
                    iconTint = lightblue60
                )
            }

        }
    }
}

@Preview
@Composable
fun ExerciseGridCardPreview() {
    FitmateTheme {
        ExerciseGridCard(FakeData.fakeExerciseData[0])
    }
}