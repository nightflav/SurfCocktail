package com.example.surfcocktailscompose.presentation.screens.editcocktailscreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun AddIngredientDialog(
    onDismiss: () -> Unit,
    onEditCurrentIngredient: (String) -> Unit,
    onAddNewIngredient: () -> Unit,
    onCancelDialog: () -> Unit,
    currIngredient: String
) {
    Dialog(onDismissRequest = {
        onDismiss()
    }) {
        Surface(
            shape = RoundedCornerShape(54.dp),
            color = MaterialTheme.colorScheme.primary
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Add ingredient",
                    fontSize = 32.sp
                )
                EditTextField(
                    contextText = "Enter ingredient",
                    haveToEnter = "title",
                    title = currIngredient,
                    isOptional = false,
                    onValueChanged = {
                        onEditCurrentIngredient(it)
                    },
                    modifier = Modifier.padding(
                        bottom = 24.dp
                    ),
                    lines = 1
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    onClick = {
                        onAddNewIngredient()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                    )
                ) {
                    Text(
                        text = "Add",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraLight,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onCancelDialog() },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.onSecondary,
                    ),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary)
                ) {
                    Text(
                        text = "Cancel",
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.ExtraLight
                    )
                }
            }
        }
    }
}