package com.tegar.fitmate

import android.content.Context
import androidx.room.Room
import com.tegar.fitmate.data.local.dao.SchenduleExerciseDao
import com.tegar.fitmate.data.local.database.FitmateDatabase
import com.tegar.fitmate.repository.SchenduleExerciseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FitmateModule {

    @Singleton
    @Provides
    fun getRepository(dao : SchenduleExerciseDao) : SchenduleExerciseRepository{
        return SchenduleExerciseRepository(dao)
    }

    @Singleton
    @Provides
    fun getDao(database : FitmateDatabase) : SchenduleExerciseDao {
        return database.schenduleDao()
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): FitmateDatabase {
        return Room.databaseBuilder(
            context.applicationContext, FitmateDatabase::class.java, "fitmate"
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }
}