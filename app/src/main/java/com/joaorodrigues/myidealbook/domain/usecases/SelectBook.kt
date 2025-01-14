package com.joaorodrigues.myidealbook.domain.usecases

import com.joaorodrigues.myidealbook.domain.model.BookModel
import com.joaorodrigues.myidealbook.domain.repository.BooksRepository
import javax.inject.Inject

class SelectBook @Inject constructor(
    private val booksRepository: BooksRepository
) {
    suspend operator fun invoke(bookId: String): BookModel? {
        return booksRepository.selectBook(bookId)
    }
}