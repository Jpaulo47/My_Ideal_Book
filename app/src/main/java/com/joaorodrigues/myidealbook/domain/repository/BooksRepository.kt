package com.joaorodrigues.myidealbook.domain.repository

import androidx.paging.PagingData
import com.joaorodrigues.myidealbook.domain.model.BookModel
import kotlinx.coroutines.flow.Flow

interface  BooksRepository {

    fun getBooks(sources: String): Flow<PagingData<BookModel>>

    fun getSuggestionBooks(sources: List<String>): Flow<PagingData<BookModel>>

    suspend fun upsertBook(book: BookModel)

    suspend fun deleteBook(book: BookModel)

    fun selectBooks(): Flow<List<BookModel>>

    suspend fun selectBook(id: String): BookModel?

    suspend fun existsBook(id: String): Boolean

}