package com.sample.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sample.rickandmorty.presentation.AppNavigator
import com.sample.rickandmorty.ui.KotlinCleanArchitectureTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            KotlinCleanArchitectureTheme(dynamicColor = false) {
                AppNavigator()
            }
        }
    }
}