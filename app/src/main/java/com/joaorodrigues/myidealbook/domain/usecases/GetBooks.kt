package com.joaorodrigues.myidealbook.domain.usecases

import androidx.paging.PagingData
import com.joaorodrigues.myidealbook.domain.model.BookModel
import com.joaorodrigues.myidealbook.domain.repository.BooksRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBooks  @Inject constructor(
    private val booksRepository: BooksRepository
) {

    operator fun invoke(sources: String): Flow<PagingData<BookModel>> {
        return booksRepository.getBooks(sources = sources)
    }

}