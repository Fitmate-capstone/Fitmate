package com.tegar.fitmate.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tegar.fitmate.data.local.faker.FakeData
import com.tegar.fitmate.data.model.Exercise
import com.tegar.fitmate.data.model.Muscle
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


    private val _muscleTargetState : MutableStateFlow<UiState<List<Muscle>>> = MutableStateFlow(UiState.Loading)
    val muscleTargetState : StateFlow<UiState<List<Muscle>>>
        get() = _muscleTargetState


    private val _activeMuscleId: MutableStateFlow<Int?> = MutableStateFlow(1)
    val activeMuscleId: StateFlow<Int?>
        get() = _activeMuscleId


    fun fetchAllRestaurants() {
        viewModelScope.launch {
            exerciseRepository.getAllExercise().catch { exception ->
                _exrcisesState.value = UiState.Error(exception.message.orEmpty())
            }.collect { exercises ->
                _exrcisesState.value = UiState.Success(exercises)
            }
        }
    }

    fun fetchListMuscle() {
        viewModelScope.launch {
            exerciseRepository.getAllMuscleCategory().catch { exception ->
                _muscleTargetState.value = UiState.Error(exception.message.orEmpty())
            }.collect { muscle ->
                _muscleTargetState.value = UiState.Success(muscle)
            }
        }
    }
    fun setActiveMuscleId(muscleId: Int) {
        _activeMuscleId.value = muscleId
    }

}
