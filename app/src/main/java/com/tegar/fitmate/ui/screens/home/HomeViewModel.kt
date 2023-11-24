package com.tegar.fitmate.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tegar.fitmate.data.model.Exercise
import com.tegar.fitmate.data.util.UiState
import com.tegar.fitmate.repository.ExerciseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val exerciseRepository: ExerciseRepository) : ViewModel() {
    private val _exrcisesState: MutableStateFlow<UiState<List<Exercise>>> = MutableStateFlow(UiState.Loading)
    val exrcisesState: StateFlow<UiState<List<Exercise>>>
        get() = _exrcisesState

    fun fetchAllRestaurants() {
        viewModelScope.launch {
            exerciseRepository.getAllExercise().catch { exception ->
                _exrcisesState.value = UiState.Error(exception.message.orEmpty())
            }.collect { exercises ->
                _exrcisesState.value = UiState.Success(exercises)
            }
        }
    }
}
