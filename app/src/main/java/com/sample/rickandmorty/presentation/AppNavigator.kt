package com.sample.rickandmorty.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sample.rickandmorty.presentation.character_details.CharacterDetailsScreen
import com.sample.rickandmorty.presentation.character_list.CharacterListScreen


@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    CleanArchitectureNavHost(navController = navController)
}

@Composable
fun CleanArchitectureNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.CharacterList
    ) {
        composable<Screen.CharacterList> {
            CharacterListScreen(
                onCharacterClick = { characterId ->
                    navController.navigate(
                        Screen.CharacterDetails(
                            characterId = characterId
                        )
                    )
                }
            )
        }

        composable<Screen.CharacterDetails> {
            CharacterDetailsScreen(
                onBackPress = {
                    navController.navigateUp()
                }
            )
        }
    }
}