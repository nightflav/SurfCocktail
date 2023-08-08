package com.example.surfcocktailscompose.presentation.screens.homescreen

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp

@Composable
fun ErrorHomeScreen(
    onError: () -> Unit
) {
    val errorRotation = rememberInfiniteTransition(label = "error_animation")
    val degrees by errorRotation.animateFloat(
        initialValue = -10F,
        targetValue = 10F,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 6000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "error_animation"
    )
    val scale by errorRotation.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800),
            repeatMode = RepeatMode.Reverse
        ),
        label = "error_animation")
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box (
            modifier = Modifier
                .rotate(degrees = degrees)
                .size((256 * scale).dp)
                .background(MaterialTheme.colorScheme.error)
                .fillMaxSize()
                .clickable { onError() },
        )
        Text(text = "Error", color = MaterialTheme.colorScheme.onError)
    }
}