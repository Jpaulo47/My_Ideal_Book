package com.joaorodrigues.myidealbook.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.joaorodrigues.myidealbook.domain.model.BookModel
import com.joaorodrigues.myidealbook.presentation.Dimens.ExtraSmallPadding2
import com.joaorodrigues.myidealbook.presentation.Dimens.MediumPadding1

@Composable
fun BooksList(
    modifier: Modifier = Modifier,
    books: List<BookModel>,
    onClick: (BookModel) -> Unit
) {
    if (books.isEmpty()) {
        EmptyScreen()
    }
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(MediumPadding1),
        contentPadding = PaddingValues(all = ExtraSmallPadding2)
    ) {
        items(
            count = books.size,
        ) {
            books[it].let { book ->
                BooksCard(book = book, onClick = { onClick(book) })
            }
        }

    }
}

@Composable
fun BooksList(
    modifier: Modifier = Modifier,
    books: LazyPagingItems<BookModel>,
    onClick: (BookModel) -> Unit
) {

    val handlePagingResult = handlePagingResult(books)

    if (handlePagingResult) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(MediumPadding1),
            contentPadding = PaddingValues(all = ExtraSmallPadding2)
        ) {
            items(
                count = books.itemCount,
                key = { index ->
                    books[index]?.id
                        ?: index
                }
            ) { index ->
                books[index]?.let { book ->
                    BooksCard(book = book, onClick = { onClick(book) })
                }
            }
        }
    }
}


@Composable
fun handlePagingResult(books: LazyPagingItems<BookModel>): Boolean {
    val loadState = books.loadState
    val error = when {
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        else -> null
    }

    return when {
        loadState.refresh is LoadState.Loading -> {
            ShimmerEffect()
            false
        }

        error != null -> {
            EmptyScreen(error = error)
            false
        }

        else -> {
            true
        }
    }
}

@Composable
fun ShimmerEffect() {
    Column(verticalArrangement = Arrangement.spacedBy(MediumPadding1)) {
        repeat(10) {
            ArticleCardShimmerEffect(
                modifier = Modifier.padding(horizontal = MediumPadding1)
            )
        }
    }
}