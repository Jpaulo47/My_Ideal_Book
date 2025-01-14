package com.joaorodrigues.myidealbook.domain.usecases

import com.joaorodrigues.myidealbook.domain.repository.BooksRepository
import javax.inject.Inject

class ExistsBook @Inject constructor(
    private val bookRepository: BooksRepository
) {
    suspend operator fun invoke(bookId: String): Boolean {
        return bookRepository.existsBook(bookId)
    }
}