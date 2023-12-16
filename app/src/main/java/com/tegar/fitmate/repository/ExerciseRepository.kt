package com.tegar.fitmate.repository

import com.google.gson.Gson
import com.tegar.fitmate.data.local.faker.FakeData
import com.tegar.fitmate.data.model.Exercise
import com.tegar.fitmate.data.model.Muscle
import com.tegar.fitmate.data.remote.model.MuscleResponse
import com.tegar.fitmate.data.remote.model.MuscleTarget
import com.tegar.fitmate.data.remote.retrofit.ExerciseApiService
import com.tegar.fitmate.data.remote.retrofit.MlApiService
import com.tegar.fitmate.data.util.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import retrofit2.HttpException
import javax.inject.Inject

class ExerciseRepository @Inject constructor(private val exerciseApiService: ExerciseApiService){

    private val exercises = mutableListOf<Exercise>()
    private val exercisesByMuscle = mutableListOf<Exercise>()


    init {
        if (exercises.isEmpty()) {
            FakeData.fakeExerciseData.forEach { exercise ->
                exercises.add(
                    Exercise(
                        exercise.id,
                        exercise.name,
                        exercise.rating,
                        exercise.level,
                        exercise.calEstimation,
                        exercise.requiredEquiment,
                        exercise.explain,
                        exercise.step,
                        exercise.category,
                        exercise.isSupportInteractive,
                        exercise.interactiveSetting,
                        exercise.interctiveBodyPartSegmentValue,
                        exercise.bodyPartNeeded,
                        exercise.muscle,
                        exercise.photo,
                        exercise.Gif
                    )
                )
            }
        }

    }



    fun getAllExercise(): Flow<List<Exercise>> {
        return flowOf(exercises)
    }

    fun getWorkoutByIdMuscle(muscleId: Int): Flow<List<Exercise>> {
        val exercisesByMuscle = exercises.filter { it.muscle.id == muscleId }
        return flowOf(exercisesByMuscle)
    }

    fun getAllMuscleCategory(): Flow<UiState<MuscleResponse>> = flow {
        emit(UiState.Loading)
        try{
            val muscleResponse = exerciseApiService.getMuscleList()
            emit(UiState.Success(muscleResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, MuscleResponse::class.java)
            errorResponse.message?.let { UiState.Error(it) }?.let { emit(it) }
        }
    }
    fun getWorkoutById(workoutId: Long): Flow<Exercise> {
        return flow {

            emit(exercises.first { it.id == workoutId })
        }
    }
}