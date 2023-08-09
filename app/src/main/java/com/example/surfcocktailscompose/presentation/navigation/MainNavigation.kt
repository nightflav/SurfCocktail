package com.example.surfcocktailscompose.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.surfcocktailscompose.di.AppComponent
import com.example.surfcocktailscompose.presentation.screens.detailsscreen.DetailsScreen
import com.example.surfcocktailscompose.presentation.screens.detailsscreen.DetailsScreenState
import com.example.surfcocktailscompose.presentation.screens.editcocktailscreen.EditCocktailScreen
import com.example.surfcocktailscompose.presentation.screens.editcocktailscreen.EditCocktailScreenState
import com.example.surfcocktailscompose.presentation.screens.homescreen.HomeScreen
import com.example.surfcocktailscompose.presentation.screens.homescreen.HomeScreenState

@Composable
fun MainNav(
    navController: NavHostController,
    appComponent: AppComponent
) {
    val homeComponent = appComponent.homeComponent().create()
    val homeViewModel = homeComponent.viewModel
    val detailsComponent = appComponent.detailComponent().create()
    val detailsViewModel = detailsComponent.detailViewModel
    val editCocktailComponent = appComponent.editCocktailComponent().create()
    val editViewModel = editCocktailComponent.viewModel

    val homeState by homeViewModel.state.collectAsState(initial = HomeScreenState())
    val detailsState by detailsViewModel.state.collectAsState(initial = DetailsScreenState())
    val editState by editViewModel.state.collectAsState(initial = EditCocktailScreenState())

    NavHost(
        navController = navController,
        startDestination = Screens.HomeScreen.argumentlessRoute,
        modifier = Modifier.background(
            MaterialTheme.colorScheme.primary
        )
    ) {
        composable(
            route = Screens.HomeScreen.route
        ) {
            HomeScreen(
                navController,
                homeState,
                homeViewModel
            )
        }
        composable(
            route = Screens.DetailsScreen.route,
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
            })
        ) {
            val id = it.arguments?.getString("id").toString()
            LaunchedEffect(key1 = Unit) { detailsViewModel.requestForCocktailById(id) }
            DetailsScreen(
                navController,
                detailsState,
                id
            )
        }
        composable(
            route = Screens.EditCocktailScreen.route,
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
            })
        ) {
            val id = it.arguments?.getString("id").toString()

            LaunchedEffect(key1 = Unit) {

            }
            EditCocktailScreen(
                navController,
                editViewModel,
                editState,
                id
            )
        }
    }
}