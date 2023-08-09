package com.example.surfcocktailscompose.presentation.screens.homescreen.extra

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.surfcocktailscompose.R

@Composable
fun EmptyHomeScreen(
    onCreateNewCocktail: () -> Unit
) {
    val arrowPosition = rememberInfiniteTransition(
        label = "arrow_animation"
    )
    val scale by arrowPosition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "arrow_animation"
    )
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.empty_screen_holder),
            contentDescription = null,
            modifier = Modifier
                .size(300.dp)
                .padding(top = 32.dp, bottom = 16.dp)
        )
        Text(
            text = stringResource(id = R.string.my_cocktails_empty_screen),
            fontSize = 36.sp,
            modifier = Modifier
                .padding(bottom = 48.dp)
        )
        Text(
            text = stringResource(id = R.string.add_first_cocktail),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .sizeIn(maxWidth = 128.dp),
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 20.sp,
            minLines = 2
        )
        Image(
            painter = painterResource(id = R.drawable.create_first_cocktail_arrow),
            contentDescription = null,
            modifier = Modifier
                .padding(top = (16 * scale).dp, bottom = ((1-scale) * 16).dp)
                .size(36.dp)
        )
        HomeFAB (
            modifier = Modifier.padding(top = 16.dp),
            onCreateNewCocktail = { onCreateNewCocktail() }
        )
    }
}

@Composable
fun HomeFAB(
    modifier: Modifier = Modifier,
    onCreateNewCocktail: () -> Unit
) {
    FloatingActionButton(
        modifier = modifier
            .size(84.dp),
        onClick = onCreateNewCocktail,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 0.dp,
        ),
        containerColor = MaterialTheme.colorScheme.secondary,
        contentColor = MaterialTheme.colorScheme.onSecondary,
        shape = RoundedCornerShape(80.dp)
    ) {
        Icon(
            painter = painterResource(
                id = R.drawable.add_icon
            ),
            contentDescription = null
        )
    }
}