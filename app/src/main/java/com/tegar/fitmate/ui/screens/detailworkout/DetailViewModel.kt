package com.tegar.fitmate.ui.screens.detailworkout

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tegar.fitmate.data.local.entity.SchenduleExerciseEntity
import com.tegar.fitmate.data.model.Exercise
import com.tegar.fitmate.data.model.SchenduleExercise
import com.tegar.fitmate.data.model.SchenduleExerciseInput
import com.tegar.fitmate.data.util.UiState
import com.tegar.fitmate.repository.ExerciseRepository
import com.tegar.fitmate.repository.SchenduleExerciseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailWorkoutViewModel @Inject constructor(
    private val repository: ExerciseRepository,
    private val schenduleExerciseRepository: SchenduleExerciseRepository

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



    fun addWorkoutSchendule(exerciseSchendule : SchenduleExerciseEntity) : String {
        var  message = ""
        viewModelScope.launch {

            val isAlreadyExist = schenduleExerciseRepository.isExerciseAlreadyExist(exerciseSchendule.dateString,exerciseSchendule.id_exercise)
            if (isAlreadyExist.isNotEmpty()) {
                message = "Exercise is already scheduled for this date."
            } else {
                message= "Exercise scheduled successfully."
                schenduleExerciseRepository.insertSchendule(exerciseSchendule)
            }


        }

        return message

    }

}