package com.example.surfcocktailscompose.presentation.screens.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.surfcocktailscompose.R
import com.example.surfcocktailscompose.data.model.CocktailDTO
import com.example.surfcocktailscompose.presentation.navigation.Screens
import com.example.surfcocktailscompose.presentation.screens.homescreen.extra.CocktailItem
import com.example.surfcocktailscompose.presentation.screens.homescreen.extra.EmptyHomeScreen
import com.example.surfcocktailscompose.presentation.screens.homescreen.extra.HomeFAB
import com.example.surfcocktailscompose.presentation.screens.homescreen.extra.LoadingHomeScreen
import com.example.surfcocktailscompose.util.Consts.CREATE_NEW_COCKTAIL_ID

@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel
) {
    val state by homeViewModel.state.collectAsState(HomeScreenState())
    when {
        state.isLoading -> {
            LoadingHomeScreen()
        }

        state.errorState -> {
            ErrorHomeScreen {
                navController.navigate(
                    route = navController.currentBackStackEntry?.id
                        ?: Screens.HomeScreen.argumentlessRoute
                )
            }
        }

        state.cocktails.isEmpty() -> {
            EmptyHomeScreen {
                navController.navigate(
                    route = Screens.EditCocktailScreen.argumentlessRoute + "/$CREATE_NEW_COCKTAIL_ID"
                )
            }
        }

        else -> {
            FulfilledHomeScreen(
                list = state.cocktails,
                onItemClicked = { id ->
                    navController.navigate(Screens.DetailsScreen.argumentlessRoute + "/$id")
                },
                onCreateNewCocktail = {
                    navController.navigate(
                        Screens.EditCocktailScreen.argumentlessRoute + "/$CREATE_NEW_COCKTAIL_ID"
                    )
                },
                onDeleteAllCocktails = {
                    homeViewModel.sendEvent(UserHomeIntents.DeleteAllCocktails)
                }
            )
        }
    }
}

@Composable
fun FulfilledHomeScreen(
    list: List<CocktailDTO>,
    onItemClicked: (String) -> Unit,
    onCreateNewCocktail: () -> Unit,
    onDeleteAllCocktails: () -> Unit
) {
    Scaffold(
        modifier = Modifier.background(
            MaterialTheme.colorScheme.background
        ),
        floatingActionButton = {
            HomeFAB(
                modifier = Modifier
                    .background(Color.Transparent),
                onCreateNewCocktail = { onCreateNewCocktail() }
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
            ) {
                LazyVerticalGrid(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(vertical = 96.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(list.size) {index ->
                        CocktailItem(
                            name = list[index].name ?: "",
                            id = list[index].id,
                            onItemClickListened = { id ->
                                onItemClicked(id)
                            }
                        )
                    }
                }
                Text(
                    text = stringResource(id = R.string.my_cocktails_empty_screen),
                    modifier = Modifier
                        .padding(24.dp)
                        .background(Color.Transparent)
                        .fillMaxWidth()
                        .clickable {
                            onDeleteAllCocktails()
                        },
                    fontSize = 36.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}