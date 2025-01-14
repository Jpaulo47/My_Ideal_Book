package com.joaorodrigues.myidealbook.domain.usecases

data class BooksUseCases(
    val getBooks: GetBooks,
    val selectBook: SelectBook,
    val upsertBook: UpsertBook,
    val deleteBook: DeleteBook,
    val selectBooks: SelectBooks,
    val existsBook: ExistsBook
)