package com.tegar.fitmate.ui.screens.detailschendule

import android.os.Build
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Light
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.tegar.fitmate.R
import com.tegar.fitmate.data.util.UiState
import com.tegar.fitmate.ui.composables.ExerciseItemSchedule
import com.tegar.fitmate.ui.screens.schendule.SchenduleViewModel
import com.tegar.fitmate.ui.theme.alertColor
import com.tegar.fitmate.ui.theme.lightblue120
import com.tegar.fitmate.ui.theme.lightblue60
import com.tegar.fitmate.ui.theme.neutral10
import com.tegar.fitmate.ui.theme.neutral80
import java.text.SimpleDateFormat
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailSchenduleScreen(
    workoutdate: String,
    navigateBack: () -> Unit,
    navigateToDetailSchedule: (workoutId: Long) -> Unit,
    detailScheduleViewModel: DetailScheduleViewModel = hiltViewModel()

) {
    val context = LocalContext.current
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { navigateBack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back to home screen"
                )
            }
            Text(workoutdate)
            Surface(
                color = neutral80,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.clickable {


                }
            ) {

                IconButton(onClick = {

                    detailScheduleViewModel.deleteScheduleByDate(workoutdate)
                    navigateBack()
                    Toast.makeText(
                        context,
                        "Exercise scheduled successfully deleted",
                        Toast.LENGTH_SHORT
                    ).show()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        tint = neutral10,

                        contentDescription = "Delete this activity"
                    )
                }


            }
        }

        Box(modifier = Modifier.padding(10.dp)) {
            detailScheduleViewModel.exerciseState.collectAsState().value.let { uiState ->
                when (uiState) {
                    is UiState.Loading -> {
                        Text(stringResource(id = R.string.loading_message))
                        detailScheduleViewModel.fetchExerciseByDate(workoutdate)
                    }

                    is UiState.Success -> {
                        if (uiState.data.isEmpty()) {
                            Text(stringResource(id = R.string.empty_exercise_message))
                        } else {
                            LazyColumn(
                                state = rememberLazyListState(),
                            ) {
                                items(uiState.data, key = { it.id }) { exercise ->


                                    val state = rememberDismissState(
                                        confirmValueChange = {
                                            if (it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd) {
                                                detailScheduleViewModel.deleteExercise(exercise)

                                                if(uiState.data.size == 1 ){
                                                    navigateBack()
                                                }


                                                true
                                            }
                                            true
                                        }
                                    )

                                    SwipeToDismiss(

                                        state = state,
                                        background = {
                                            val color = when (state.dismissDirection) {
                                                DismissDirection.EndToStart -> alertColor
                                                DismissDirection.StartToEnd ->  alertColor
                                                null -> Color.Transparent
                                            }
                                            Box(
                                                modifier = Modifier
                                                    .background(color)
                                                    .fillMaxSize()
                                                    .padding(10.dp)

                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Delete,
                                                    contentDescription = "Delete",
                                                    modifier = Modifier.align(Alignment.CenterEnd)
                                                )
                                                Icon(
                                                    imageVector = Icons.Default.Delete,
                                                    contentDescription = "Delete",
                                                    modifier = Modifier.align(Alignment.CenterStart)
                                                )
                                            }

                                        },
                                        dismissContent = {
                                            ExerciseItemSchedule(
                                                exercise = exercise,
                                                navigateToDetailSchedule = { id ->
                                                    navigateToDetailSchedule(id)
                                                })
                                        })

                                }
                            }
                        }

                    }

                    is UiState.Error -> {
                        Text(stringResource(id = R.string.error_message))
                    }
                }


            }
        }
    }

}

