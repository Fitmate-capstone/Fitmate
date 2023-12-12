package com.tegar.fitmate.repository.type

import com.tegar.fitmate.data.local.entity.SchenduleExerciseEntity
import com.tegar.fitmate.data.model.SchenduleExercise
import com.tegar.fitmate.data.model.SchenduleExerciseInput
import kotlinx.coroutines.flow.Flow

interface SchenduleExerciseType {

    suspend fun insertSchendule(exercise: SchenduleExerciseEntity)
     fun getAllSchendule() :Flow<List<SchenduleExercise>>
    fun getAllExerciseByDate(date : String) :Flow<List<SchenduleExerciseEntity>>

}