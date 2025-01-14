package com.joaorodrigues.myidealbook.domain.usecases

import com.joaorodrigues.myidealbook.domain.model.BookModel
import com.joaorodrigues.myidealbook.domain.repository.BooksRepository
import javax.inject.Inject

class DeleteBook @Inject constructor(
    private val repository: BooksRepository
) {
    suspend operator fun invoke(book: BookModel) {
        repository.deleteBook(book)
    }
}