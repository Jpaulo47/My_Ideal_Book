package com.joaorodrigues.myidealbook.presentation.home

import androidx.paging.PagingData
import com.joaorodrigues.myidealbook.domain.model.BookModel
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val searchQuery: String = "",
    val books: Flow<PagingData<BookModel>>? = null,
)