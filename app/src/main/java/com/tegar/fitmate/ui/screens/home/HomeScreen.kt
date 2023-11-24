package com.tegar.fitmate.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tegar.fitmate.R
import com.tegar.fitmate.data.util.UiState
import com.tegar.fitmate.di.Injection
import com.tegar.fitmate.ui.composables.ExerciseHorizontalCard
import com.tegar.fitmate.ui.composables.ListSection
import com.tegar.fitmate.ui.screens.ViewModelFactory
import com.tegar.fitmate.ui.theme.satoshiFontFamily

@Composable
fun HomeScreen(
    navigateToDetail: (Long) -> Unit,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    modifier: Modifier = Modifier,

    ) {

   LazyColumn(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
       item {

           viewModel.exrcisesState.collectAsState(initial = UiState.Loading).value.let { uiState ->
               when (uiState) {
                   is UiState.Loading -> {
                       Text(stringResource(id = R.string.loading_message))
                       viewModel.fetchAllRestaurants()
                   }

                   is UiState.Success -> {

                       if (uiState.data.isEmpty()) {
                           Text(stringResource(id = R.string.empty_exercise_message))
                       } else {
                           ListSection(
                               title = stringResource(id = R.string.section_favorite_title),
                               subtitle = stringResource(
                                   id = R.string.section_favorite_subtitle
                               )
                           ) {
                               LazyRow(
                                   horizontalArrangement = Arrangement.spacedBy(16.dp),
                                   contentPadding = PaddingValues(horizontal = 16.dp),
                               ) {
                                   items(uiState.data, key = { it.name }) { exercise ->
                                       ExerciseHorizontalCard(exercise = exercise)
                                   }
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


