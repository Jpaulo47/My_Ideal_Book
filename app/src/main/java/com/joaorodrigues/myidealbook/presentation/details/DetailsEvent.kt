package com.joaorodrigues.myidealbook.presentation.details

import com.joaorodrigues.myidealbook.domain.model.BookModel

sealed class DetailsEvent {

    data class UpsertDeleteArticle(val book: BookModel) : DetailsEvent()

    object RemoveSideEffect : DetailsEvent()

}