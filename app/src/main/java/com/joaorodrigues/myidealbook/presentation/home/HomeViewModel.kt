package com.joaorodrigues.myidealbook.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.joaorodrigues.myidealbook.BuildConfig
import com.joaorodrigues.myidealbook.data.local.cache.PreferencesManager
import com.joaorodrigues.myidealbook.domain.usecases.BooksUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val booksUseCases: BooksUseCases,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _uiState: MutableStateFlow<HomeUIState> = MutableStateFlow(HomeUIState.Initial)
    val uiState = _uiState.asStateFlow()

    private val _state = mutableStateOf(SearchState())
    val state: State<SearchState> = _state

    val books = BuildConfig.apiKey
    init {
        viewModelScope.launch {
            val lastSearchQuery = preferencesManager.getLastSearchQuery()
            searchBooks(lastSearchQuery)
        }
    }

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.UpdateSearchQuery -> {
                _state.value = state.value.copy(searchQuery = event.searchQuery)
            }

            is SearchEvent.SearchBooks -> {
                searchBooks(state.value.searchQuery)
                viewModelScope.launch {
                    preferencesManager.saveLastSearchQuery(state.value.searchQuery)
                }
            }
        }
    }

    private fun searchBooks(search: String) {
        if (search.isBlank()) {
            _uiState.value = HomeUIState.Error("A consulta não pode estar vazia.")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                _uiState.value = HomeUIState.Loading
                val books = booksUseCases.getBooks(search).cachedIn(viewModelScope)
                _state.value = state.value.copy(books = books)
                _uiState.value = if ( books == null) {
                    HomeUIState.Error("Nenhum resultado encontrado para: $search")
                } else {
                    HomeUIState.Success("Resultados carregados para: $search")
                }
            } catch (e: Exception) {
                _uiState.value = HomeUIState.Error("Erro ao carregar os resultados: ${e.message}")
            }
        }
    }

}

sealed interface HomeUIState {
    data object Initial : HomeUIState
    data object Loading : HomeUIState
    data class Error(val message: String) : HomeUIState
    data class Success(val message: String) : HomeUIState
}