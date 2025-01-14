package com.joaorodrigues.myidealbook.domain.usecases

import com.joaorodrigues.myidealbook.domain.model.BookModel
import com.joaorodrigues.myidealbook.domain.repository.BooksRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SelectBooks @Inject constructor(
    private val booksRepository: BooksRepository
){

    operator fun invoke(): Flow<List<BookModel>>{
        return booksRepository.selectBooks()
    }
}