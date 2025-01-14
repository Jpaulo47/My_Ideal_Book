package com.joaorodrigues.myidealbook.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.joaorodrigues.myidealbook.domain.model.BookModel
import com.joaorodrigues.myidealbook.presentation.details.DetailsScreen
import com.joaorodrigues.myidealbook.presentation.details.DetailsViewModel
import com.joaorodrigues.myidealbook.presentation.favorite.FavoritePage
import com.joaorodrigues.myidealbook.presentation.favorite.FavoriteViewModel
import com.joaorodrigues.myidealbook.presentation.home.DrawerContent
import com.joaorodrigues.myidealbook.presentation.home.HomePage
import com.joaorodrigues.myidealbook.presentation.home.HomeViewModel
import com.joaorodrigues.myidealbook.presentation.nvgraph.Route
import com.joaorodrigues.myidealbook.ui.theme.NavItem
import kotlinx.coroutines.launch

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navItemList = listOf(
        NavItem("Home", Icons.Default.Home),
        NavItem("Favorites", Icons.Default.Favorite)
    )

    var selectedIndex by remember { mutableIntStateOf(0) }
    var isTopAppBarVisible by remember { mutableStateOf(true) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navController = rememberNavController()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { DrawerContent() },
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                if (isTopAppBarVisible) {
                    TopAppBar(
                        title = { Text(text = "My Ideal Book", style = MaterialTheme.typography.titleLarge) },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                            }
                        }
                    )
                }
            },
            bottomBar = {
                NavigationBar {
                    navItemList.forEachIndexed { index, navItem ->
                        NavigationBarItem(
                            selected = selectedIndex == index,
                            onClick = { selectedIndex = index },
                            icon = { Icon(imageVector = navItem.icon, contentDescription = null) },
                            label = { Text(text = navItem.label, style = MaterialTheme.typography.bodySmall) }
                        )
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                composable(route = Route.HomeScreen.route) {
                    ContentScreen(
                        modifier = Modifier.fillMaxSize(),
                        selectedIndex = selectedIndex,
                        nav = navController
                    )
                }
                composable(route = Route.FavoriteScreen.route) {
                    ContentScreen(
                        modifier = Modifier.fillMaxSize(),
                        selectedIndex = selectedIndex,
                        nav = navController
                    )
                }
                composable(route = Route.DetailScreen.route) {
                    val viewModel: DetailsViewModel = hiltViewModel()
                    navController.previousBackStackEntry?.savedStateHandle?.get<BookModel>("book")
                        ?.let { book ->
                            isTopAppBarVisible = false
                            viewModel.existsBook(book)
                            val existedBook = viewModel.existedBook.value
                            DetailsScreen(
                                book = book,
                                event = viewModel::onEvent,
                                navigateUp = {
                                    navController.navigateUp()
                                    isTopAppBarVisible = true
                                },
                                sideEffect = viewModel.sideEffect,
                                existedBook = existedBook
                            )
                        }
                }
            }
        }
    }
}

@Composable
fun ContentScreen(modifier: Modifier = Modifier, selectedIndex: Int, nav: NavController) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val state = homeViewModel.state.value
    val favoriteViewModel: FavoriteViewModel = hiltViewModel()
    val stateFavorite = favoriteViewModel.state.value

    Column(modifier = modifier.fillMaxSize()) {
        when (selectedIndex) {
            0 -> HomePage(
                onEvent = homeViewModel::onEvent,
                searchState = state,
                navigateToDetails = { book -> navigateToDetails(navController = nav, book = book) }
            )
            1 -> FavoritePage(
                state = stateFavorite,
                navigateToDetails = { book -> navigateToDetails(navController = nav, book = book) }
            )
        }
    }
}

private fun navigateToDetails(navController: NavController, book: BookModel) {
    navController.currentBackStackEntry?.savedStateHandle?.set("book", book)
    navController.navigate(route = Route.DetailScreen.route)
}
