package com.sample.kotlin.cleanarchitecture.ui.screen

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList()
) {
    data object CharacterList : Screen("character_list")

    data class CharacterDetails(val id: String) : Screen(
        route = "character_details/$id",
        navArguments = listOf(navArgument("id") {
            type = NavType.StringType
        })
    ) {
        companion object {
            fun createRoute(id: String) = "character_details/$id"
        }
    }
}
