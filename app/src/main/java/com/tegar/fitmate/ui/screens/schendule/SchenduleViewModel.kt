package com.tegar.fitmate.ui.screens.schendule

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tegar.fitmate.data.model.SchenduleExercise
import com.tegar.fitmate.data.util.UiState
import com.tegar.fitmate.repository.ExerciseRepository
import com.tegar.fitmate.repository.SchenduleExerciseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchenduleViewModel @Inject constructor(
    private val schenduleExerciseRepository: SchenduleExerciseRepository
) : ViewModel() {
    private val _schenduleState: MutableStateFlow<UiState<List<SchenduleExercise>>> =
        MutableStateFlow(UiState.Loading)
    val schenduleState: StateFlow<UiState<List<SchenduleExercise>>>
        get() = _schenduleState  .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState.Loading
        )



    fun fetchAllSchenduleWorkout() {
        viewModelScope.launch {
            schenduleExerciseRepository.getAllSchendule().catch {exception ->
                _schenduleState.value = UiState.Error(exception.message.orEmpty())

            }.collect{ schendule ->
                _schenduleState.value = UiState.Success(schendule)

            }
        }
    }


}