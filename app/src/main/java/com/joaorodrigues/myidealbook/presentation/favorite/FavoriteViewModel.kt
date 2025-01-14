package com.joaorodrigues.myidealbook.presentation.favorite

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaorodrigues.myidealbook.domain.usecases.BooksUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val booksUseCases: BooksUseCases
): ViewModel(){

    private val _state = mutableStateOf(FavoriteState())
    val state: State<FavoriteState> = _state

    init {
        getBooks()
    }

    private fun getBooks() {
        booksUseCases.selectBooks().onEach { books ->
            _state.value = _state.value.copy(books = books.asReversed())
        }.launchIn(viewModelScope)
    }
}