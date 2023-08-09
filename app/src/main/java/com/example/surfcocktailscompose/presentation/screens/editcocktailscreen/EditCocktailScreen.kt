package com.example.surfcocktailscompose.presentation.screens.editcocktailscreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.surfcocktailscompose.R
import com.example.surfcocktailscompose.presentation.navigation.Screens
import com.example.surfcocktailscompose.presentation.screens.homescreen.ErrorHomeScreen
import com.example.surfcocktailscompose.presentation.screens.homescreen.extra.LoadingHomeScreen

@Composable
fun EditCocktailScreen(
    navController: NavHostController,
    viewModel: EditCocktailViewModel,
) {
    val state by viewModel.state.collectAsState(initial = EditCocktailScreenState())
    when {
        state.isLoading -> LoadingHomeScreen()
        state.errorState -> ErrorHomeScreen {}
        state.showDialog -> {
            AddIngredientDialog(
                onDismiss = { viewModel.events.trySend(UserEditCocktailIntents.CloseDialog) },
                onEditCurrentIngredient = {
                    viewModel.events.trySend(
                        UserEditCocktailIntents.EditCurrentIngredient(it)
                    )
                },
                onAddNewIngredient = {
                    viewModel.events.trySend(UserEditCocktailIntents.AddNewIngredient)
                },
                onCancelDialog = {
                    viewModel.events.trySend(UserEditCocktailIntents.CloseDialog)
                },
                currIngredient = state.currIngredient
            )
        }

        else -> {
            EditScreen(
                state = state,
                onEditName = {
                    viewModel.events.trySend(UserEditCocktailIntents.EditName(it))
                },
                onEditRecipe = {
                    viewModel.events.trySend(UserEditCocktailIntents.EditRecipe(it))
                },
                onEditDescription = {
                    viewModel.events.trySend(UserEditCocktailIntents.EditDescription(it))
                },
                onOpenDialog = {
                    viewModel.events.trySend(
                        UserEditCocktailIntents.OpenDialog
                    )
                },
                onCancel = {
                    navController.popBackStack()
                },
                onSave = {
                    viewModel.events.trySend(
                        UserEditCocktailIntents.SaveNewCocktail
                    )
                    navController.popBackStack()
                },
                onDeleteIngredient = {
                    viewModel.events.trySend(UserEditCocktailIntents.DeleteIngredient(it))
                }
            )
        }
    }
}

@Composable
fun EditScreen(
    state: EditCocktailScreenState,
    onEditName: (String) -> Unit,
    onEditDescription: (String) -> Unit,
    onEditRecipe: (String) -> Unit,
    onOpenDialog: () -> Unit,
    onCancel: () -> Unit,
    onSave: () -> Unit,
    onDeleteIngredient: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(64.dp))
        Box(
            modifier = Modifier
                .size(215.dp)
                .background(
                    MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(54.dp)
                ),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(id = R.drawable.add_photo_icon),
                contentDescription = null,
                modifier = Modifier.background(
                    MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(54.dp)
                )
            )
        }
        EditTextField(
            modifier = Modifier,
            lines = 1,
            contextText = "Cocktail name",
            title = state.name,
            isOptional = false,
            haveToEnter = "title"
        ) {
            onEditName(it)
        }
        EditTextField(
            modifier = Modifier,
            lines = 5,
            contextText = "Description",
            title = state.description,
            isOptional = true
        ) {
            onEditDescription(it)
        }
        Ingredients(
            ingredients = state.ingredients,
            onDeleteIngredient = onDeleteIngredient,
            onOpenDialog = onOpenDialog)

        EditTextField(
            modifier = Modifier,
            lines = 5,
            contextText = "Recipe",
            title = state.recipe,
            isOptional = true,
            onValueChanged = {
                onEditRecipe(it)
            }
        )

        Button(
            modifier = Modifier
                .height(56.dp)
                .padding(bottom = 8.dp)
                .fillMaxWidth(),
            onClick = onSave,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
            )
        ) {
            Text(text = "Save", color = MaterialTheme.colorScheme.onSecondary)
        }
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            onClick = onCancel,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = MaterialTheme.colorScheme.onSecondary,

                )
        ) {
            Text(text = "Cancel", color = MaterialTheme.colorScheme.secondary)
        }
    }
}

@Composable
fun EditTextField(
    lines: Int,
    modifier: Modifier = Modifier,
    contextText: String,
    title: String,
    isOptional: Boolean,
    haveToEnter: String = "",
    onValueChanged: (String) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier
    )
    {
        OutlinedTextField(
            minLines = lines,
            maxLines = 6,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            value = title,
            onValueChange = { onValueChanged(it) },
            label = {
                Text(text = contextText, Modifier.background(MaterialTheme.colorScheme.primary))
            },
            isError = !isOptional,
            shape = RoundedCornerShape(34.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                cursorColor = MaterialTheme.colorScheme.secondary,
                unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary
            )
        )
        if (isOptional)
            Text(
                text = "Optional field",
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp, bottom = 0.dp)
            )
        else
            Text(
                text = "Input $haveToEnter",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(start = 16.dp)
            )
    }
}

@Composable
fun Ingredients(
    ingredients: Array<String>,
    onDeleteIngredient: (String) -> Unit,
    onOpenDialog: () -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(start = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(ingredients.size) { index ->
            Card(
                modifier = Modifier
                    .height(36.dp)
                    .widthIn(max = 256.dp),
                shape = RoundedCornerShape(100),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onPrimary)
            ) {
                Row {
                    Text(
                        text = ingredients[index],
                        fontSize = 12.sp,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(8.dp)
                    )
                    IconButton(onClick = { onDeleteIngredient(ingredients[index]) }) {
                        Icon(
                            Icons.Default.Close,
                            null,
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }
        }
        item {
            IconButton(
                onClick = {
                    onOpenDialog()
                },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                ),
                modifier = Modifier.size(36.dp),
            ) {
                Icon(
                    Icons.Default.Add,
                    null,
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    }
}