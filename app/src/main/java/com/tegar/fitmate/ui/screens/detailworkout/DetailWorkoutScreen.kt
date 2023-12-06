package com.tegar.fitmate.ui.screens.detailworkout

import android.os.Build.VERSION.SDK_INT
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Flip
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.VideoFrameDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.tegar.fitmate.R
import com.tegar.fitmate.data.model.Exercise
import com.tegar.fitmate.data.util.UiState
import com.tegar.fitmate.di.Injection
import com.tegar.fitmate.ui.screens.ViewModelFactory
import com.tegar.fitmate.ui.theme.lightblue60
import com.tegar.fitmate.ui.theme.neutral10
import com.tegar.fitmate.ui.theme.neutral30
import com.tegar.fitmate.ui.theme.neutral80
import com.tegar.fitmate.ui.util.formatSteps

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailWorkoutScreen(
    workoutId: Long,
    navigateBack: () -> Unit,
    navigateToInteractiveArea: (Long) -> Unit,

    viewModel: DetailWorkoutViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),

    ) {

    val exerciseState by viewModel.exercise.collectAsState(initial = UiState.Loading)
    when (exerciseState) {
        is UiState.Loading -> {
            viewModel.getWorkoutById(workoutId)
        }

        is UiState.Success -> {
            val data = (exerciseState as UiState.Success<Exercise>).data



            DetailContent(
                navigateBack,
                data,
                navigateToInteractiveArea

            )
        }

        is UiState.Error -> {
            Text(stringResource(id = R.string.error_message))
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContent(
    navigateBack: () -> Unit,
    exercise: Exercise,
    navigateToInteractiveArea: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    Box(
        modifier = modifier.fillMaxSize(),
    ) {


        Column(
            Modifier
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
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
                Text(exercise.name)
                Surface(
                    color = neutral80,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.clickable {
                        isSheetOpen = true

                    }
                ) {
                    Box(Modifier.padding(8.dp)) {
                        Icon(
                            painterResource(id = R.drawable.ic_schendule_inactive),
                            tint = neutral10,

                            contentDescription = "Schendule to learn this"
                        )
                    }

                }
            }
            WorkoutTutorial()
            Spacer(modifier = Modifier.height(100.dp))
            WorkoutInformationTab(exercise)


        }

        if (isSheetOpen) {
            ModalBottomSheet(
                containerColor = neutral80,
                contentColor = neutral10,
                sheetState = sheetState,
                onDismissRequest = {
                    isSheetOpen = false
                }) {
                Text("Schendule to learn this")
                Text("Schendule to learn this")
                Text("Schendule to learn this")
                Text("Schendule to learn this")
                Text("Schendule to learn this")
                Text("Schendule to learn this")
                Text("Schendule to learn this")

            }
        }
        AnimatedVisibility(
            visible = true,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically(),
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.background
                )
                .padding(
                    vertical = 16.dp,
                    horizontal = 24.dp
                )
                .fillMaxWidth()

                .align(Alignment.BottomStart)
        ) {

            if (exercise.isSupportInteractive) {
                FilledTonalButton(
                    onClick = {
                        navigateToInteractiveArea(exercise.id)
                    },
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 2.dp
                    ),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(
                        vertical = 14.dp,
                        horizontal = 49.dp
                    ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = neutral80,
                        contentColor = neutral10
                    )
                ) {
                    Text(
                        stringResource(id = R.string.start_interactive_text),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontFamily = FontFamily.Default,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }


        }
    }

}

@Composable
fun GifImage(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    Image(

        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(context).data(data = R.drawable.dummy_2).apply(block = {
                size(Size.ORIGINAL)
            }).build(), imageLoader = imageLoader
        ),
        contentDescription = null,
        modifier = modifier.fillMaxWidth(),
    )

}

@Composable
fun WorkoutTutorial(modifier: Modifier = Modifier) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    val tabTitles = listOf("Animation", "Video")

    Column {
        // Tab Row
        TabRow(
            selectedTabIndex = selectedTabIndex,
            contentColor = Color.White,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    color = lightblue60,
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
                )
            },
            modifier = Modifier.padding(
                horizontal = 60.dp
            )
        ) {
            // Create tabs
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                    }
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }

        // Content based on selected tab
        Box(modifier.padding(16.dp)) {
            when (selectedTabIndex) {
                0 -> GifImage()
                1 -> WorkoutVideo()
            }
        }

    }
}

@Composable
fun WorkoutVideo() {
    Text("No available for now", style = MaterialTheme.typography.bodyMedium)

}

@Composable
fun WorkoutInformationTab(exercise: Exercise) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    val tabTitles = listOf("Overview", "Step by Step", "Muscle Target")

    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
        // Tab Row
        TabRow(
            selectedTabIndex = selectedTabIndex,
            contentColor = Color.White,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    color = lightblue60,
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
                )
            }
        ) {
            // Create tabs
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                    }
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }

        // Content based on selected tab
        when (selectedTabIndex) {
            0 -> OverviewContent(exercise.explain)
            1 -> StepByStepContent(exercise.step)
            2 -> MuscleTargetContent()
        }
    }
}

@Composable
fun OverviewContent(explain: String) {
    // Content for Overview tab
    Text(
        explain, style = MaterialTheme.typography.bodyMedium.copy(
            color = neutral30
        )
    )
}

@Composable
fun StepByStepContent(step: Array<String>) {
    val formattedSteps = formatSteps(step)

    // Content for Step by Step tab
    Text(
        formattedSteps, style = MaterialTheme.typography.bodyMedium.copy(
            color = neutral30
        )
    )
}

@Composable
fun MuscleTargetContent() {
    // Content for Muscle Target tab
    Text("Muscle Target content goes here")
}
