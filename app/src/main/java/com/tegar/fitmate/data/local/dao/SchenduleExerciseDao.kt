package com.tegar.fitmate.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.tegar.fitmate.data.local.entity.SchenduleExerciseEntity
import com.tegar.fitmate.data.model.SchenduleExercise
import com.tegar.fitmate.data.model.SchenduleExerciseInput

@Dao
interface SchenduleExerciseDao {

    @Query("SELECT * FROM schendule_exercise WHERE dateString = :selectedDate")
    fun getExercisesByDate(selectedDate: String): List<SchenduleExerciseEntity>

    @Query("SELECT id,id_exercise, dateString, GROUP_CONCAT(exercise_category) as categories,  " +
            "CASE " +
            "WHEN COUNT(DISTINCT exercise_muscle_target) = 1 THEN GROUP_CONCAT(exercise_muscle_target) " +
            "WHEN COUNT(DISTINCT exercise_muscle_target) = 2 THEN GROUP_CONCAT(exercise_muscle_target, ' and ') " +
            "WHEN COUNT(DISTINCT exercise_muscle_target) > 2 THEN 'Mixed' END as muscleTarget, " +
            "isFinished, SUM(exercise_calori) as totalCalories, COUNT(*) as exerciseCount " +
            "FROM schendule_exercise GROUP BY dateString ORDER BY dateMillis ASC")
    fun getSummaryByDate(): List<SchenduleExercise>



    @Insert
    suspend fun insertExercise(exercise: SchenduleExerciseEntity)

    @Delete
    suspend fun deleteExercise(exercise: SchenduleExerciseEntity)
}