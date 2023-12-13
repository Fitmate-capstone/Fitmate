package com.tegar.fitmate.repository.type

import com.tegar.fitmate.data.local.entity.SchenduleExerciseEntity
import com.tegar.fitmate.data.model.SchenduleExercise
import com.tegar.fitmate.data.model.SchenduleExerciseInput
import kotlinx.coroutines.flow.Flow

interface SchenduleExerciseType {

    suspend fun insertSchendule(exercise: SchenduleExerciseEntity)
     fun getAllSchendule() :Flow<List<SchenduleExercise>>


    fun isExerciseAlreadyExist(date : String, exerciseId : Long) : List<SchenduleExerciseEntity>
    fun getAllExerciseByDate(date : String) :Flow<List<SchenduleExerciseEntity>>

    suspend fun deleteScheduleByDate(date: String)
    suspend fun deleteExercise(exercise: SchenduleExerciseEntity)
}