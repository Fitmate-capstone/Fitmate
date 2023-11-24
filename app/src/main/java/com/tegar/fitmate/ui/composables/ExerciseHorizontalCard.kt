package com.tegar.fitmate.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.FlashOff
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.FlashOff
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tegar.fitmate.R
import com.tegar.fitmate.data.local.faker.FakeData
import com.tegar.fitmate.data.model.Exercise
import com.tegar.fitmate.ui.theme.FitmateTheme
import com.tegar.fitmate.ui.theme.lightblue60
import com.tegar.fitmate.ui.theme.neutral30


@Composable
fun ExerciseHorizontalCard(exercise: Exercise, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .width(300.dp)
            .height(140.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            val contrast = 0.6f
            val colorMatrix = floatArrayOf(
                contrast, 0f, 0f, 0f, 0f,
                0f, contrast, 0f, 0f, 0f,
                0f, 0f, contrast, 0f, 0f,
                0f, 0f, 0f, 1f, 0f
            )
            Image(
                painter = painterResource(id = exercise.photo),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                colorFilter = ColorFilter.colorMatrix(ColorMatrix(colorMatrix)),
            )

            // Text Informasi
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                // Informasi di Atas Card
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Informasi Kategori dan Target Otot
                    Text(
                        text = "${exercise.muscle.name} | ${exercise.category.name} ",
                        style =  MaterialTheme.typography.labelMedium.copy(
                            color = neutral30
                        ))

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

                // Informasi di Bawah Card
                Column(){
                    // Nama Latihan
                    Text(
                        text = exercise.name,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(9.dp))

                   Row(horizontalArrangement  = Arrangement.spacedBy(8.dp)){
                       // Informasi Tambahan
                       ExerciseAdditionInformation(
                           icon = Icons.Default.LocalFireDepartment,
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
    }
}


@Composable
fun ExerciseAdditionInformation(
    icon: ImageVector,
    text: String,
    iconTint: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = text ,  style = MaterialTheme.typography.labelSmall.copy(
            color = Color.White,
            fontWeight = FontWeight.Medium
        ))
    }
}

@Preview
@Composable
fun ExerciseHorizontalCardPreview() {
    FitmateTheme {
        ExerciseHorizontalCard(FakeData.fakeExerciseData[0])
    }
}