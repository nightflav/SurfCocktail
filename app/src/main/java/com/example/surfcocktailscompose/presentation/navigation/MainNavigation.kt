package com.example.surfcocktailscompose.presentation.navigation

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.surfcocktailscompose.App
import com.example.surfcocktailscompose.di.AppComponent
import com.example.surfcocktailscompose.presentation.screens.detailsscreen.DetailsScreen
import com.example.surfcocktailscompose.presentation.screens.homescreen.HomeScreen
import com.example.surfcocktailscompose.presentation.screens.editcocktailscreen.EditCocktailScreen

@Composable
fun MainNav(
    navController: NavHostController,
    appComponent: AppComponent
) {
    NavHost(
        navController = navController,
        startDestination = Screens.HomeScreen.route
    ) {
        composable(
            route = Screens.HomeScreen.route
        ) {
            val homeComponent = appComponent.homeComponent().create()
            val viewModel = homeComponent.viewModel
            HomeScreen(
                navController = navController,
                homeViewModel = viewModel
            )
        }
        composable(
            route = Screens.DetailsScreen.route,
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
            })
        ) {
            val detailsComponent = appComponent.detailComponent().create()
            val viewModel = detailsComponent.detailViewModel
            DetailsScreen(
                navController,
                viewModel
            )
        }
        composable(
            route = Screens.EditCocktailScreen.route,
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
            })
        ) {
            val editCocktailComponent = appComponent.editCocktailComponent().create()
            val viewModel = editCocktailComponent.viewModel
            EditCocktailScreen(
                navController,
                viewModel
            )
        }
    }
}