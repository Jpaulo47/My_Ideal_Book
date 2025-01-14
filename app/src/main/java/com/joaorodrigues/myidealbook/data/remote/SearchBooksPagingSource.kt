package com.joaorodrigues.myidealbook.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.joaorodrigues.myidealbook.domain.model.BookModel

class SearchBooksPagingSource(
    private val booksApi: BooksApi,
    private val searchQuery: String
) : PagingSource<Int, BookModel>() {

    private val loadedIds = mutableSetOf<String>()

    override fun getRefreshKey(state: PagingState<Int, BookModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BookModel> {
        val page = params.key ?: 1
        return try {
            val newsResponse = booksApi.searchBooks(query = searchQuery)
            val books = newsResponse.items.filter { loadedIds.add(it.id) }

            LoadResult.Page(
                data = books,
                nextKey = if (books.isEmpty() || loadedIds.size >= newsResponse.totalItems) null else page + 1,
                prevKey = if (page == 1) null else page - 1
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(throwable = e)
        }
    }
}
