package com.example.surfcocktailscompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.surfcocktailscompose.di.DaggerAppComponent
import com.example.surfcocktailscompose.presentation.navigation.MainNav
import com.example.surfcocktailscompose.presentation.ui.theme.SurfCocktailsComposeTheme
import com.example.surfcocktailscompose.util.getAppComponent

class MainActivity : ComponentActivity() {

    private lateinit var navHostController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appComponent = getAppComponent()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        Log.d("TAGTAGTAG", "Here")
        setContent {
            SurfCocktailsComposeTheme {
                navHostController = rememberNavController()
                MainNav(
                    navController = navHostController,
                    appComponent = appComponent
                )
            }
        }
    }
}