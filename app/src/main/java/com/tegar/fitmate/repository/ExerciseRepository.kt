package com.tegar.fitmate.repository

import com.tegar.fitmate.data.local.faker.FakeData
import com.tegar.fitmate.data.model.Exercise
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ExerciseRepository {

    private val exercises = mutableListOf<Exercise>()
    init {
        if (exercises.isEmpty()) {
            FakeData.fakeExerciseData.forEach { exercise ->
                exercises.add(
                    Exercise(
                        exercise.name,
                        exercise.rating,
                        exercise.level,
                        exercise.calEstimation,
                        exercise.requiredEquiment,
                        exercise.explain,
                        exercise.step,
                        exercise.category,
                        exercise.muscle,

                        exercise.photo
                    )
                )
            }
        }
    }
    fun getAllExercise(): Flow<List<Exercise>> {
        return flowOf(exercises)
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