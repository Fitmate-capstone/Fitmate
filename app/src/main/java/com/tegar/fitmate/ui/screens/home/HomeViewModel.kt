package com.tegar.fitmate.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tegar.fitmate.data.local.faker.FakeData
import com.tegar.fitmate.data.model.Exercise
import com.tegar.fitmate.data.model.Muscle
import com.tegar.fitmate.data.remote.model.MuscleResponse
import com.tegar.fitmate.data.util.UiState
import com.tegar.fitmate.repository.ExerciseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val exerciseRepository: ExerciseRepository) : ViewModel() {
    private val _exrcisesState: MutableStateFlow<UiState<List<Exercise>>> = MutableStateFlow(UiState.Loading)
    val exrcisesState: StateFlow<UiState<List<Exercise>>>
        get() = _exrcisesState


    private val _discoverExerciseState: MutableStateFlow<UiState<List<Exercise>>> = MutableStateFlow(UiState.Loading)
    val discoverExerciseState: StateFlow<UiState<List<Exercise>>>
        get() = _discoverExerciseState


    private val _muscleTargetState : MutableStateFlow<UiState<MuscleResponse>> = MutableStateFlow(UiState.Loading)
    val muscleTargetState : StateFlow<UiState<MuscleResponse>>
        get() = _muscleTargetState


    private val _activeMuscleId: MutableStateFlow<Int?> = MutableStateFlow(1)
    val activeMuscleId: StateFlow<Int?>
        get() = _activeMuscleId


    fun fetchListWorkout() {
        viewModelScope.launch {
            exerciseRepository.getAllExercise().catch { exception ->
                _exrcisesState.value = UiState.Error(exception.message.orEmpty())
            }.collect { exercises ->
                _exrcisesState.value = UiState.Success(exercises)
            }
        }
    }

    fun fetchWorkoutListByIdMuscle(muscleId : Int) {
        viewModelScope.launch {
            exerciseRepository.getWorkoutByIdMuscle(muscleId).catch { exception ->
                _discoverExerciseState.value = UiState.Error(exception.message.orEmpty())
            }.collect { exercises ->
                _discoverExerciseState.value = UiState.Success(exercises)
            }
        }
    }

    fun fetchListMuscle() {

        viewModelScope.launch {

            exerciseRepository.getAllMuscleCategory().catch { exception ->
                _muscleTargetState.value = UiState.Error(exception.message.orEmpty())
            }.collect { muscle ->
                _muscleTargetState.value = muscle
            }
        }
    }
    fun setActiveMuscleId(muscleId: Int) {
        _discoverExerciseState.value = UiState.Loading

        _activeMuscleId.value = muscleId

    }

}
