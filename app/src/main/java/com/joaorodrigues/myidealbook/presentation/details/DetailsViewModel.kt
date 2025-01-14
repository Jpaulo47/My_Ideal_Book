package com.joaorodrigues.myidealbook.presentation.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaorodrigues.myidealbook.domain.model.BookModel
import com.joaorodrigues.myidealbook.domain.usecases.DeleteBook
import com.joaorodrigues.myidealbook.domain.usecases.ExistsBook
import com.joaorodrigues.myidealbook.domain.usecases.SelectBook
import com.joaorodrigues.myidealbook.domain.usecases.UpsertBook
import com.joaorodrigues.myidealbook.util.UIComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val selectArticle: SelectBook,
    private val deleteArticleUseCase: DeleteBook,
    private val upsertArticleUseCase: UpsertBook,
    private val existsBookUseCase: ExistsBook
) : ViewModel() {

    var sideEffect by mutableStateOf<UIComponent?>(null)
        private set

    private val _existedBook = mutableStateOf(false)
    val existedBook get() = _existedBook

    fun onEvent(event: DetailsEvent) {
        when (event) {
            is DetailsEvent.UpsertDeleteArticle -> {
                viewModelScope.launch {
                    val book = selectArticle(bookId = event.book.id)
                    if (book == null){
                        upsertBook(book = event.book)
                    }else{
                        deleteBook(book = event.book)
                    }
                }
            }
            is DetailsEvent.RemoveSideEffect ->{
                sideEffect = null
            }
        }
    }

    fun existsBook(book: BookModel) {
        viewModelScope.launch {
            _existedBook.value = existsBookUseCase(book.id)
        }
    }

    private suspend fun deleteBook(book: BookModel) {
        deleteArticleUseCase(book = book)
        sideEffect = UIComponent.Toast("Book deleted")
    }

    private suspend fun upsertBook(book: BookModel) {
        upsertArticleUseCase(book = book)
        sideEffect = UIComponent.Toast("Book Inserted")
    }

}