package com.tegar.fitmate.ui

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.lifecycle.lifecycleScope

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
import com.tegar.fitmate.ui.screens.profile.ProfileViewModel
import com.tegar.fitmate.ui.screens.routelist.ScreenRoute
import com.tegar.fitmate.ui.screens.schendule.SchenduleScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.tegar.fitmate.ui.screens.detailschendule.DetailSchenduleScreen
import com.tegar.fitmate.ui.screens.equimentsearch.EquimentSearchScreen
import com.tegar.fitmate.ui.screens.interactivelearn.InteractiveLearnScreen
import com.tegar.fitmate.ui.screens.sign_in.GoogleAuthClient
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FitmateApp(
    navController: NavHostController = rememberNavController(),
    googleAuthUiClient: GoogleAuthClient,
    onSignInClick: () -> Unit,
    onLogoutClick: () -> Unit
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
                AppBar(navigatToEquimentSearch = {
                    navController.navigate(ScreenRoute.EquimentSearch.route)

                })
            }
        },
        bottomBar = {

            if (currentRoute in routesWithBottomBar) {
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
            composable(
                ScreenRoute.DetailWorkout.route,
                arguments = listOf(
                    navArgument("workoutId") { type = NavType.LongType }),
            ) {
                val workoutId = it.arguments?.getLong("workoutId") ?: -1L
                val context = LocalContext.current
                DetailWorkoutScreen(
                    workoutId = workoutId,
                    navigateBack = { navController.navigateUp() },
                    navigateToInteractiveArea = { workoutId ->
                        navController.navigate(ScreenRoute.InteractiveLearn.createRoute(workoutId))

                    }
                )
            }

            composable(ScreenRoute.Explore.route) {

                ExploreScreen()
            }
            composable(
                ScreenRoute.InteractiveLearn.route,
                arguments = listOf(
                    navArgument("workoutId") { type = NavType.LongType }),
            ) {
                val workoutId = it.arguments?.getLong("workoutId") ?: -1L
                val context = LocalContext.current
                InteractiveLearnScreen(
                    workoutId = workoutId,
                    navigateBack = { navController.navigateUp() },
                )
            }
            composable(ScreenRoute.Schendule.route) {

                SchenduleScreen(
                    navigateToDetail = { workoutdate ->
                        navController.navigate(ScreenRoute.DetailSchedule.createRoute(workoutdate))
                    },
                )
            }
            composable(
                ScreenRoute.DetailSchedule.route,
                arguments = listOf(
                    navArgument("workoutdate") { type = NavType.StringType }),
            ) {
                val workoutdate = it.arguments?.getString("workoutdate") ?: ""
                DetailSchenduleScreen(
                    workoutdate = workoutdate,
                    navigateToDetailSchedule = { workoutId ->
                        navController.navigate(ScreenRoute.DetailWorkout.createRoute(workoutId))
                    },
                    navigateBack = { navController.navigateUp() },
                )
            }

            composable(ScreenRoute.EquimentSearch.route) {
                EquimentSearchScreen()
            }
            composable(ScreenRoute.Profile.route) {
                val viewModel = viewModel<ProfileViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()

//                LaunchedEffect(key1 = Unit) {
//                    if (googleAuthUiClient.getSignedInUser() == null) {
//                        navController.navigate("sign_in")
//                    }
//                }

                ProfileScreen(
                    userData = googleAuthUiClient.getSignedInUser(),
                    onSignIn = onSignInClick,
                    onSignOut = onLogoutClick,
                    redirectToHome = {
                        navController.navigate("home")
                    }
                )
            }


        }

    }


}

