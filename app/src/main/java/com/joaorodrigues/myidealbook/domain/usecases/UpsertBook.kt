package com.joaorodrigues.myidealbook.domain.usecases

import com.joaorodrigues.myidealbook.domain.model.BookModel
import com.joaorodrigues.myidealbook.domain.repository.BooksRepository
import javax.inject.Inject

class UpsertBook @Inject constructor(
    private val booksRepository: BooksRepository
){

    suspend operator fun invoke(book: BookModel){
        booksRepository.upsertBook(book)
    }
}