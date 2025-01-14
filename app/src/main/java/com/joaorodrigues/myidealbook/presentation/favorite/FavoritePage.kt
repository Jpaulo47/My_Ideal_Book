package com.joaorodrigues.myidealbook.presentation.favorite

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.joaorodrigues.myidealbook.domain.model.BookModel
import com.joaorodrigues.myidealbook.presentation.Dimens.MediumPadding1
import com.joaorodrigues.myidealbook.presentation.common.BooksList

@Composable
fun FavoritePage(
    state: FavoriteState,
    navigateToDetails: (BookModel) -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(start = MediumPadding1, end = MediumPadding1)
    ) {

        BooksList(
            books = state.books,
            onClick = {navigateToDetails(it)}
        )
    }
}