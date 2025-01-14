package com.joaorodrigues.myidealbook.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.joaorodrigues.myidealbook.data.local.BooksDao
import com.joaorodrigues.myidealbook.data.remote.BooksApi
import com.joaorodrigues.myidealbook.domain.repository.BooksRepository
import com.joaorodrigues.myidealbook.data.remote.SearchBooksPagingSource
import com.joaorodrigues.myidealbook.domain.model.BookModel
import kotlinx.coroutines.flow.Flow

class BooksRepositoryImpl(
    private val booksApi: BooksApi,
    private val booksDao: BooksDao
): BooksRepository {
    override fun getBooks(sources: String): Flow<PagingData<BookModel>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                SearchBooksPagingSource(
                    booksApi = booksApi,
                    searchQuery = sources
                )
            }
        ).flow
    }

    override fun getSuggestionBooks(sources: List<String>): Flow<PagingData<BookModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun upsertBook(book: BookModel) {
        booksDao.upsert(book)
    }

    override suspend fun deleteBook(book: BookModel) {
        booksDao.delete(book)
    }

    override fun selectBooks(): Flow<List<BookModel>> {
        return booksDao.getBooks()
    }

    override suspend fun selectBook(id: String): BookModel? {
        return booksDao.getBook(id)
    }

    override suspend fun existsBook(id: String): Boolean {
        return booksDao.existsBook(id)
    }
}