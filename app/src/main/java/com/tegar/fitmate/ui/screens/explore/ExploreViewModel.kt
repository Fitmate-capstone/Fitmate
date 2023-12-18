package com.tegar.fitmate.ui.screens.explore

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tegar.fitmate.data.remote.model.ExerciseResponse
import com.tegar.fitmate.data.util.UiState
import com.tegar.fitmate.repository.ExerciseRepository
import com.tegar.fitmate.repository.SchenduleExerciseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ExploreViewModel @Inject constructor(private val exerciseRepository: ExerciseRepository, private val schenduleExerciseRepository: SchenduleExerciseRepository
) : ViewModel()  {

    private val _exercise: MutableStateFlow<UiState<ExerciseResponse>> = MutableStateFlow(
        UiState.Loading)
    val exercise: StateFlow<UiState<ExerciseResponse>>
        get() = _exercise


    var searchQuery by mutableStateOf("")
        private set

    fun getExercise(query : String) {

        viewModelScope.launch {

                exerciseRepository.searchExercise(query).catch { exception ->
                    _exercise.value = UiState.Error(exception.message.orEmpty())

                }.collect { exercises ->
                    _exercise.value = exercises
                }


        }
    }

    fun getTopExercise() {
        viewModelScope.launch {
            exerciseRepository.getTopExercise(20).catch { exception ->
                _exercise.value = UiState.Error(exception.message.orEmpty())

            }.collect { exercises ->
                _exercise.value = exercises
            }
        }
    }
}