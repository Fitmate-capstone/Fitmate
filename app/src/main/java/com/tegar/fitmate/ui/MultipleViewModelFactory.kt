package com.tegar.fitmate.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tegar.fitmate.repository.ExerciseRepository
import com.tegar.fitmate.repository.SchenduleExerciseRepository
import com.tegar.fitmate.ui.screens.detailworkout.DetailWorkoutViewModel
import com.tegar.fitmate.ui.screens.equimentsearch.EquimentSearchViewModel
import com.tegar.fitmate.ui.screens.home.HomeViewModel
import com.tegar.fitmate.ui.screens.interactivelearn.InteractiveLearnViewModel
import com.tegar.fitmate.ui.screens.schendule.SchenduleViewModel

class MultipleViewModelFactory  (private val exerciseRepository:  ExerciseRepository,private val schenduleExerciseRepository: SchenduleExerciseRepository) :
    ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {

            if (modelClass.isAssignableFrom(DetailWorkoutViewModel::class.java)) {
                return DetailWorkoutViewModel(exerciseRepository, schenduleExerciseRepository ) as T
            }

            throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
}