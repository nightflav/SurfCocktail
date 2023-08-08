package com.example.surfcocktailscompose.presentation.screens.editcocktailscreen

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.surfcocktailscompose.presentation.navigation.Screens
import com.example.surfcocktailscompose.presentation.screens.homescreen.ErrorHomeScreen
import com.example.surfcocktailscompose.presentation.screens.homescreen.extra.LoadingHomeScreen

@Composable
fun EditCocktailScreen(
    navController: NavHostController,
    viewModel: EditCocktailViewModel,
    id: String
) {
    val state by viewModel.state.collectAsState(initial = EditCocktailScreenState())
    viewModel.sendEvent(UserEditCocktailIntents.Init(id))

    when {
        state.isLoading -> LoadingHomeScreen()
        state.errorState -> ErrorHomeScreen {}
        else -> {
            EditScreen(
                state = state, viewModel = viewModel, id = id,
                onCancel = {
                    navController.popBackStack()
                },
                onSave = {
                    viewModel.sendEvent(
                        UserEditCocktailIntents.SaveNewCocktail
                    )
                    navController.navigate(Screens.HomeScreen.argumentlessRoute)
                }
            )
        }
    }
}

@Composable
fun EditScreen(
    state: EditCocktailScreenState,
    viewModel: EditCocktailViewModel,
    id: String,
    onCancel: () -> Unit,
    onSave: () -> Unit
) {
    val scrollState = rememberScrollState(0)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.scrollable(scrollState, Orientation.Vertical)
    ) {
        TextField(
            modifier = Modifier.padding(bottom = 60.dp),
            value = state.name,
            onValueChange = {
                viewModel.sendEvent(
                    UserEditCocktailIntents.EditName(
                        id = id,
                        name = it
                    )
                )
            },
            label = {
                Text(text = "Title")
            },
            shape = RoundedCornerShape(34.dp),
            supportingText = {
                Text(text = "Cocktail name", color = MaterialTheme.colorScheme.onBackground)
            }
        )
        TextField(
            value = state.description,
            onValueChange = {
                viewModel.sendEvent(
                    UserEditCocktailIntents.EditDescription(
                        id = id,
                        desc = it
                    )
                )
            },
            label = {
                Text(text = "Description")
            }
        )
        Text(text = "Optional field")
        LazyHorizontalGrid(rows = GridCells.Adaptive(24.dp)) {
            items(state.ingredients.size) {
                Card {
                    Text(
                        text = state.ingredients[it],
                        fontSize = 12.sp
                    )
                }
            }
            item {
                Button(onClick = {
                    viewModel.sendEvent(
                        UserEditCocktailIntents.OpenDialog(id)
                    )
                }) {
                    Icon(Icons.Default.Add, null)
                }
            }
        }
        TextField(
            value = state.recipe,
            onValueChange = {
                viewModel.sendEvent(
                    UserEditCocktailIntents.EditRecipe(
                        id = id,
                        recipe = it
                    )
                )
            },
            label = {
                Text(text = "Recipe")
            }
        )
        Text(text = "OptionalField")
        Button(onClick = onSave) {
            Text(text = "Save")
        }
        Button(onClick = onCancel) {
            Text(text = "Cancel")
        }
    }

}