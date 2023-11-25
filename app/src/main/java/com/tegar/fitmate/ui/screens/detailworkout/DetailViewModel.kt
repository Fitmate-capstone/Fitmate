package com.tegar.fitmate.ui.screens.detailworkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tegar.fitmate.data.model.Exercise
import com.tegar.fitmate.data.util.UiState
import com.tegar.fitmate.repository.ExerciseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DetailWorkoutViewModel(
    private val repository: ExerciseRepository
) : ViewModel() {

    private val _exercise: MutableStateFlow<UiState<Exercise>> =
        MutableStateFlow(UiState.Loading)
    val exercise: StateFlow<UiState<Exercise>>
        get() = _exercise

    fun getWorkoutById(workoutId: Long) {
        viewModelScope.launch {
            repository.getWorkoutById(workoutId).catch { exception ->
                _exercise.value = UiState.Error(exception.message.orEmpty())
            }.collect{ exercise ->
                _exercise.value = UiState.Success(exercise)
            }

        }
    }

}