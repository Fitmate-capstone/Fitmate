package com.tegar.fitmate.di

import com.tegar.fitmate.repository.ExerciseRepository

object Injection {
    fun provideRepository(): ExerciseRepository {
        return ExerciseRepository.getInstance()
    }
}