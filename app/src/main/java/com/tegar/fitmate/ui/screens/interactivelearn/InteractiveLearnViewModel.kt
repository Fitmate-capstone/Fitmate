package com.tegar.fitmate.ui.screens.interactivelearn

import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.tegar.fitmate.data.model.Exercise
import com.tegar.fitmate.data.util.UiState
import com.tegar.fitmate.repository.ExerciseRepository
import com.tegar.fitmate.ui.state.InteractiveUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InteractiveLearnViewModel(private val repository: ExerciseRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(InteractiveUiState())
    val uiState: StateFlow<InteractiveUiState> = _uiState.asStateFlow()

    private val _exercise: MutableStateFlow<UiState<Exercise>> =
        MutableStateFlow(UiState.Loading)

    private var timer: CountDownTimer? = null
    private val initialTime = MutableLiveData<Long>()
    private val currentTime = MutableLiveData<Long>()
    val exercise: StateFlow<UiState<Exercise>>
        get() = _exercise

    private val _canPlaySound: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val canPlaySound: StateFlow<Boolean>
        get() = _canPlaySound

     var currentTimeString by mutableStateOf("")




    private val _onCounting: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val onCounting: StateFlow<Boolean>
        get() = _onCounting

    private val _eventCountDownFinish = MutableLiveData<Boolean>()
    val eventCountDownFinish: LiveData<Boolean> = _eventCountDownFinish


    fun getWorkoutById(workoutId: Long) {
        viewModelScope.launch {
            repository.getWorkoutById(workoutId).catch { exception ->
                _exercise.value = UiState.Error(exception.message.orEmpty())
            }.collect { exercise ->
                _exercise.value = UiState.Success(exercise)
            }

        }
    }



    private fun updateTimeString(millisUntilFinished: Long) {
        currentTimeString =    DateUtils.formatElapsedTime(millisUntilFinished / 1000)
    }
    fun startTimer() {
        timer?.start()

        val initialTimeMillis = 10  * 1000
        initialTime.value = initialTimeMillis.toLong()
        currentTime.value = initialTimeMillis.toLong()

        timer = object : CountDownTimer(initialTimeMillis.toLong(), 1000) {

            override fun onTick(millisUntilFinished: Long) {
                currentTime.value = millisUntilFinished
                updateTimeString(millisUntilFinished)
                _onCounting.value = true
            }
            override fun onFinish() {
                resetTimer()
                playExercise()
            }
        }
    }

    fun resetTimer() {
        timer?.cancel()
        currentTime.value = initialTime.value
        _eventCountDownFinish.value = true
        _onCounting.value = false


    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }


    fun playSound() {
        startTimer()
    }


    fun playExercise() {
        _uiState.update { currentState ->
            currentState.copy(
                isTutorialScreen = false
            )

        }
    }

    fun increaseCount() {
        _uiState.update { currentState ->
            if(!currentState.isTutorialScreen) {
                currentState.copy(
                    counter = currentState.counter + 1
                )
            }else{
                currentState.copy(
                    counter = 0
                )
            }


        }
    }
}