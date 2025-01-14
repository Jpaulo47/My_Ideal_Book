package com.joaorodrigues.myidealbook.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.joaorodrigues.myidealbook.domain.model.BookModel
import com.joaorodrigues.myidealbook.presentation.common.BooksList
import com.joaorodrigues.myidealbook.presentation.common.SearchBar
import com.joaorodrigues.myidealbook.presentation.Dimens.MediumPadding1

@Composable
fun HomePage(
    onEvent: (SearchEvent) -> Unit,
    searchState: SearchState,
    navigateToDetails: (BookModel) -> Unit
) {

    val viewModel: HomeViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        SearchBar(
            text = searchState.searchQuery,
            readOnly = false,
            onValueChange = { onEvent(SearchEvent.UpdateSearchQuery(it)) },
            onSearch = {
                onEvent(SearchEvent.SearchBooks)
            }
        )

        Spacer(modifier = Modifier.height(MediumPadding1))

        Text(
            modifier = Modifier.padding(bottom = 16.dp),
            textAlign = TextAlign.Center,
            text = when (uiState) {
                is HomeUIState.Initial -> "Digite algo para realizar a busca"
                is HomeUIState.Loading -> "Carregando resultados..."
                is HomeUIState.Error -> (uiState as HomeUIState.Error).message
                is HomeUIState.Success -> (uiState as HomeUIState.Success).message
            }
        )

        Spacer(modifier = Modifier.height(MediumPadding1))

        searchState.books?.let { it ->
            val books = it.collectAsLazyPagingItems()
            BooksList(
                books = books, onClick = { navigateToDetails(it) }
            )
        }

    }
}