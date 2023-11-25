package com.tegar.fitmate.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tegar.fitmate.ui.composables.AppBar
import com.tegar.fitmate.ui.composables.BottomBar
import com.tegar.fitmate.ui.screens.detailworkout.DetailWorkoutScreen
import com.tegar.fitmate.ui.screens.explore.ExploreScreen
import com.tegar.fitmate.ui.screens.home.HomeScreen
import com.tegar.fitmate.ui.screens.profile.ProfileScreen
import com.tegar.fitmate.ui.screens.routelist.ScreenRoute
import com.tegar.fitmate.ui.screens.schendule.SchenduleScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FitmateApp(
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val routesWithBottomBar = listOf(
        ScreenRoute.Home.route,
        ScreenRoute.Profile.route,
        ScreenRoute.Explore.route,
        ScreenRoute.Schendule.route
    )

    Scaffold(
        topBar = {

            if (currentRoute == ScreenRoute.Home.route) {
                AppBar()
            }
        },
        bottomBar = {

            if (currentRoute  in routesWithBottomBar ) {
                if (currentRoute != null) {
                    BottomBar(navController = navController, currentRoute = currentRoute)
                }
            }
    }) { paddingValue ->
        NavHost(
            navController = navController,
            startDestination = ScreenRoute.Home.route,
            modifier = Modifier.padding(paddingValue)
        ) {
            composable(ScreenRoute.Home.route) {
                HomeScreen(
                    navigateToDetail = { workoutId ->
                        navController.navigate(ScreenRoute.DetailWorkout.createRoute(workoutId))
                    },
                )
            }
            composable(ScreenRoute.DetailWorkout.route,                arguments = listOf(
                navArgument("workoutId") { type = NavType.LongType }),
            ) {
                val workoutId = it.arguments?.getLong("workoutId") ?: -1L
                val context = LocalContext.current
                DetailWorkoutScreen(
                    workoutId = workoutId,
                    navigateBack = { navController.navigateUp() },
                )
            }

            composable(ScreenRoute.Explore.route) {

                ExploreScreen()
            }
            composable(ScreenRoute.Schendule.route) {

                SchenduleScreen()
            }
            composable(ScreenRoute.Profile.route) {

                ProfileScreen()
            }

        }

    }


}