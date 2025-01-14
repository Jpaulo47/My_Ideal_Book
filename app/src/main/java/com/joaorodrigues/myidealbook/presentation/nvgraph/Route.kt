package com.joaorodrigues.myidealbook.presentation.nvgraph

sealed class Route(
    val route: String
) {

    object HomeScreen: Route(route = "home")
    object DetailScreen: Route(route = "detailScreen")
    object FavoriteScreen: Route(route = "favorite")

}