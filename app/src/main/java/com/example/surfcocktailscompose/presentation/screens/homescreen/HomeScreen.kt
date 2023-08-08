package com.example.surfcocktailscompose.presentation.screens.homescreen

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.surfcocktailscompose.R
import com.example.surfcocktailscompose.presentation.navigation.Screens
import com.example.surfcocktailscompose.util.Consts.CREATE_NEW_COCKTAIL_ID

@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = viewModel()
) {
    val state by homeViewModel.state.collectAsState(HomeScreenState())
    when {
        state.isLoading -> {
            LoadingHomeScreen()
        }

        state.errorState -> {
            ErrorHomeScreen()
        }

        state.cocktails.isEmpty() -> {
            EmptyHomeScreen {
                navController.navigate(route = Screens.EditCocktailScreen.route + CREATE_NEW_COCKTAIL_ID)
            }
        }

        else -> {}
    }
}

@Composable
fun ErrorHomeScreen() {
    
}

@Composable
fun LoadingHomeScreen() {

}