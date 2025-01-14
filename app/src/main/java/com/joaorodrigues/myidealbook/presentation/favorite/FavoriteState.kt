package com.joaorodrigues.myidealbook.presentation.favorite

import com.joaorodrigues.myidealbook.domain.model.BookModel

data class FavoriteState(
    val books: List<BookModel> = emptyList()
)