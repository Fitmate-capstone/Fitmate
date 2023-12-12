package com.tegar.fitmate.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tegar.fitmate.data.local.dao.SchenduleExerciseDao
import com.tegar.fitmate.data.local.entity.SchenduleExerciseEntity


@Database(entities = [SchenduleExerciseEntity::class], version = 1, exportSchema = false)
abstract class FitmateDatabase  : RoomDatabase(){

    abstract fun schenduleDao() : SchenduleExerciseDao


}