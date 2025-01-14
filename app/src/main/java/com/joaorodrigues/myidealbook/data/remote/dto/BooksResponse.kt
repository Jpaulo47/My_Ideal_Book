package com.joaorodrigues.myidealbook.data.remote.dto

import com.joaorodrigues.myidealbook.domain.model.BookModel

data class BooksResponse(
    val items: List<BookModel>,
    val kind: String,
    val totalItems: Int
)