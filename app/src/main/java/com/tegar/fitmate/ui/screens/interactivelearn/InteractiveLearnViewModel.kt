package com.tegar.fitmate.ui.screens.interactivelearn

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
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


    private val _maxRepetition: MutableStateFlow<Int> = MutableStateFlow(0)
    val maxRepetition: StateFlow<Int>
        get() = _maxRepetition

    private val _maxSet: MutableStateFlow<Int> = MutableStateFlow(0)
    val maxSet: StateFlow<Int>
        get() = _maxSet

    private val _eventCountDownFinish = MutableLiveData<Boolean>()
    val eventCountDownFinish: LiveData<Boolean> = _eventCountDownFinish


    fun getWorkoutById(workoutId: Long) {
        viewModelScope.launch {
            repository.getWorkoutById(workoutId).catch { exception ->
                _exercise.value = UiState.Error(exception.message.orEmpty())
            }.collect { exercise ->
                _exercise.value = UiState.Success(exercise)
                _maxRepetition.value = exercise.interactiveSetting.repetion
                _maxSet.value = exercise.interactiveSetting.set
            }

        }
    }


    private fun updateTimeString(millisUntilFinished: Long) {
        currentTimeString = DateUtils.formatElapsedTime(millisUntilFinished / 1000)
    }

    fun startTimer(time: Long = 10) {
        timer?.start()

        val initialTimeMillis = time * 1000
        initialTime.value = initialTimeMillis
        currentTime.value = initialTimeMillis

        timer = object : CountDownTimer(initialTimeMillis, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                currentTime.value = millisUntilFinished
                updateTimeString(millisUntilFinished)
                _onCounting.value = true
            }

            override fun onFinish() {
                resetTimer()

                if (_uiState.value.currentRest == _maxSet.value) {
                    stopExercise()
                } else {
                    playExercise()

                }
            }
        }
    }

    fun resetTimer() {
        timer?.cancel()
        currentTime.value = initialTime.value
        _eventCountDownFinish.value = true
        _onCounting.value = false


    }


    fun playExercise() {
        _uiState.update { currentState ->
            currentState.copy(
                isTutorialScreen = false,
                isInRestMode = false,
            )

        }
    }

    fun stopExercise() {
        _uiState.update { currentState ->
            currentState.copy(
                counter = 0,
                isFinished = true,
                isTutorialScreen = false,
                isInRestMode = true,
            )

        }
    }

    fun increaseCount() {

        if (_uiState.value.counter == maxRepetition.value) {
            _uiState.update { currentState ->
                currentState.copy(
                    counter = 0,
                    currentRest = currentState.currentRest + 1,
                    isInRestMode = true
                )
            }
            if (_uiState.value.counter == 0 && _uiState.value.isInRestMode  && !_uiState.value.isFinished ) {
                startTimer(50)
            }
        } else {
            _uiState.update { currentState ->
                if (!currentState.isTutorialScreen) {
                    currentState.copy(
                        counter = currentState.counter + 1
                    )
                } else {
                    currentState.copy(
                        counter = 0
                    )
                }


            }
        }


    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

}