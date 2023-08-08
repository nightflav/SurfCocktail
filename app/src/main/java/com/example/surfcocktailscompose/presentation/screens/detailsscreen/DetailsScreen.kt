package com.example.surfcocktailscompose.presentation.screens.detailsscreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.surfcocktailscompose.R
import com.example.surfcocktailscompose.data.model.CocktailDTO
import com.example.surfcocktailscompose.presentation.navigation.Screens
import com.example.surfcocktailscompose.presentation.screens.homescreen.ErrorHomeScreen
import com.example.surfcocktailscompose.presentation.screens.homescreen.extra.LoadingHomeScreen

@Composable
fun DetailsScreen(
    navController: NavHostController,
    viewModel: DetailsViewModel,
    id: String
) {
    val state by viewModel.state.collectAsState(initial = DetailsScreenState())
    viewModel.requestForCocktailById(id)
    when {
        state.isLoading -> {
            LoadingHomeScreen()
        }

        state.errorState -> {
            ErrorHomeScreen {
                navController.navigate(Screens.HomeScreen.route)
            }
        }

        else -> {
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = null
                )
                BottomSheetScreen(cocktail = state.cocktail) {
                    navController.navigate(Screens.EditCocktailScreen.route + "/$id")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomSheetScreen(
    cocktail: CocktailDTO,
    onEditListener: () -> Unit
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            DetailsInfo(cocktail = cocktail) {
                onEditListener()
            }
        },
        sheetPeekHeight = 500.dp,
        sheetShape = RoundedCornerShape(
            topStart = 54.dp, topEnd = 54.dp
        )
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = null
        )
    }
}

@Composable
fun DetailsInfo(
    modifier: Modifier = Modifier,
    cocktail: CocktailDTO,
    onEditListener: () -> Unit
) {
    Surface(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn {
                item {
                    Text(
                        text = cocktail.name, fontSize = 32.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
                item {
                    Text(
                        text = cocktail.description, fontSize = 16.sp,
                        modifier = Modifier.padding(32.dp)
                    )
                }
                val ingredients = cocktail.ingredients

                items(ingredients.size) {
                    Text(text = ingredients[it], fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "-", fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                }

                item { Text(text = cocktail.recipe, fontSize = 16.sp) }
            }
            Button(onClick = { onEditListener() }) {

            }
        }
    }
}