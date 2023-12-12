package com.tegar.fitmate.repository

import com.tegar.fitmate.data.local.dao.SchenduleExerciseDao
import com.tegar.fitmate.data.local.entity.SchenduleExerciseEntity
import com.tegar.fitmate.data.model.SchenduleExercise
import com.tegar.fitmate.data.model.SchenduleExerciseInput
import com.tegar.fitmate.repository.type.SchenduleExerciseType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class SchenduleExerciseRepository @Inject constructor(private val db: SchenduleExerciseDao) : SchenduleExerciseType{
    override suspend fun insertSchendule(exercise: SchenduleExerciseEntity) {
        db.insertExercise(exercise)
    }
    override  fun getAllSchendule() : Flow<List<SchenduleExercise>> {
       return flowOf(db.getSummaryByDate())
    }

    override  fun getAllExerciseByDate(date : String) : Flow<List<SchenduleExerciseEntity>> {
        return flowOf(db.getExercisesByDate(date))
    }


    companion object {
        @Volatile
        private var instance: SchenduleExerciseRepository? = null
        fun getInstance(db  : SchenduleExerciseDao): SchenduleExerciseRepository =
            instance ?: synchronized(this) {
                SchenduleExerciseRepository(db).apply {
                    instance = this
                }
            }

    }
}