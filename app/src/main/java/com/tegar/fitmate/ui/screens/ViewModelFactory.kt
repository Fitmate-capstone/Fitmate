package com.tegar.fitmate.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tegar.fitmate.repository.ExerciseRepository
import com.tegar.fitmate.repository.SchenduleExerciseRepository
import com.tegar.fitmate.ui.screens.detailworkout.DetailWorkoutViewModel
import com.tegar.fitmate.ui.screens.equimentsearch.EquimentSearchViewModel
import com.tegar.fitmate.ui.screens.home.HomeViewModel
import com.tegar.fitmate.ui.screens.interactivelearn.InteractiveLearnViewModel

class ViewModelFactory(private val repository: ExerciseRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
//            return HomeViewModel(repository) as T
//        }
//        if (modelClass.isAssignableFrom(EquimentSearchViewModel::class.java)) {
//            return EquimentSearchViewModel() as T
//        }
//        if (modelClass.isAssignableFrom(DetailWorkoutViewModel::class.java)) {
//            return DetailWorkoutViewModel(repository ) as T
//        }
//        if (modelClass.isAssignableFrom(InteractiveLearnViewModel::class.java)) {
//            return InteractiveLearnViewModel(repository) as T
//        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}