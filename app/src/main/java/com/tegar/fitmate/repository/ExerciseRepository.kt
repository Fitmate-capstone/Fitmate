package com.tegar.fitmate.repository

import com.tegar.fitmate.data.local.faker.FakeData
import com.tegar.fitmate.data.model.Exercise
import com.tegar.fitmate.data.model.Muscle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class ExerciseRepository {

    private val exercises = mutableListOf<Exercise>()
    private val exercisesByMuscle = mutableListOf<Exercise>()

    private val muscles= mutableListOf<Muscle>()

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
                        exercise.photo
                    )
                )
            }
        }

        if (muscles.isEmpty()) {
            FakeData.fakeMuscleData.forEach { muscle ->
                muscles.add(muscle)

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

    fun getAllMuscleCategory(): Flow<List<Muscle>> {
        return flowOf(muscles)
    }
    fun getWorkoutById(workoutId: Long): Flow<Exercise> {
        return flow {

            emit(exercises.first { it.id == workoutId })
        }
    }
    companion object {
        @Volatile
        private var instance: ExerciseRepository? = null
        fun getInstance(): ExerciseRepository =
            instance ?: synchronized(this) {
                ExerciseRepository().apply {
                    instance = this
                }
            }

    }
}