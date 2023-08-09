package com.example.surfcocktailscompose.presentation.screens.detailsscreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
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
    when {
        state.isLoading -> {
            LoadingHomeScreen()
        }

        state.errorState -> {
            ErrorHomeScreen {
                navController.navigate(Screens.HomeScreen.argumentlessRoute)
            }
        }

        else -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = null
                )
                BottomSheetScreen(cocktail = state.cocktail) {
                    navController.navigate(Screens.EditCocktailScreen.argumentlessRoute + "/$id")
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
        Column {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(2f),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.weight(1f))
        }
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
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Text(
                        text = cocktail.name.toString(), fontSize = 32.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
                item {
                    Text(
                        text = cocktail.description.toString(), fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 32.dp)
                    )
                }
                val ingredients = cocktail.ingredients

                items(ingredients!!.size) {
                    Text(text = ingredients[it], fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "-", fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                }

                item { Text(text = "Recipe:\n" + cocktail.recipe.toString(), fontSize = 16.sp) }
            }
            Button(
                onClick = { onEditListener() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Text(text = "Edit", color = MaterialTheme.colorScheme.onSecondary)
            }
        }
    }
}