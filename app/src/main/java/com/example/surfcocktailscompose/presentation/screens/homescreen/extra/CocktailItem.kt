package com.example.surfcocktailscompose.presentation.screens.homescreen.extra

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.surfcocktailscompose.presentation.screens.homescreen.extra.TransparentBrush.transpBrush
import com.example.surfcocktailscompose.util.Consts.LOADING_ID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CocktailItem(
    name: String,
    id: String,
    modifier: Modifier = Modifier,
    brush: Brush = transpBrush,
    onItemClickListened: (String) -> Unit
) {
    Card(
        modifier = modifier
            .size(164.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = RoundedCornerShape(54.dp),
        onClick = { onItemClickListened(id) }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
                .background(brush),
            contentAlignment = Alignment.BottomCenter
        ) {
            if (brush === transpBrush && id != LOADING_ID)
                Text(
                    text = name,
                    modifier = Modifier.paddingFromBaseline(bottom = 32.dp),
                )
        }
    }
}

private object TransparentBrush {
    val transpBrush = Brush.linearGradient(
        colors = listOf(
            Color.Transparent,
            Color.Transparent
        )
    )
}