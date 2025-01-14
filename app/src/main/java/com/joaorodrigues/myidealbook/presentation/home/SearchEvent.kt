package com.joaorodrigues.myidealbook.presentation.home

sealed class SearchEvent {

    data class UpdateSearchQuery(val searchQuery: String): SearchEvent()

    object SearchBooks: SearchEvent()
}