package com.tegar.fitmate.ui.screens.routelist

sealed class ScreenRoute(val route: String) {
    object Home : ScreenRoute("home")
    object Explore : ScreenRoute("explore")
    object Profile : ScreenRoute("profile")


    object DetailWorkout : ScreenRoute("home/{workoutId}") {
        fun createRoute(workoutId: Long) = "home/$workoutId"
    }

    object InteractiveLearn : ScreenRoute("detailworkout/{workoutId}") {
        fun createRoute(workoutId: Long) = "detailworkout/$workoutId"
    }

    object Schendule : ScreenRoute("schedule")
    object OnBoarding : ScreenRoute("onboarding")
    object EquimentSearch : ScreenRoute("equiment-search")

    val requiresBottomBar: Boolean
        get() = this in listOf(Home, Profile, Explore, Schendule)


}