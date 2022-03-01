package com.alexeyyuditsky.test.app.view.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.app.model.Book
import com.alexeyyuditsky.test.app.model.BooksRepository
import com.alexeyyuditsky.test.foundation.model.PendingResult
import com.alexeyyuditsky.test.foundation.model.SuccessResult
import com.alexeyyuditsky.test.foundation.views.BaseViewModel
import com.alexeyyuditsky.test.foundation.views.LiveResult
import com.alexeyyuditsky.test.foundation.views.MediatorLiveResult
import com.alexeyyuditsky.test.foundation.views.MutableLiveResult
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import com.alexeyyuditsky.test.foundation.sideeffects.navigator.Navigator
import com.alexeyyuditsky.test.foundation.sideeffects.resources.Resources
import com.alexeyyuditsky.test.foundation.sideeffects.toasts.Toasts

class EditBookViewModel(
    private val screen: EditBookFragment.Screen,
    private val navigator: Navigator,
    private val toasts: Toasts,
    private val resources: Resources,
    private val booksRepository: BooksRepository,
) : BaseViewModel() {

    private val _book = MutableLiveResult<Book>(PendingResult())
    val book: LiveResult<Book> = _book

    private val _viewState = MediatorLiveResult<ViewState>()
    val viewState: LiveResult<ViewState> = _viewState

    private val _saveInProgress = MutableLiveData(false)

    val screenTitle: LiveData<String> = Transformations.map(_book) { result ->
        if (result is SuccessResult) {
            resources.getString(R.string.view_model_description_fragment_title, result.data.name)
        } else {
            resources.getString(R.string.book)
        }
    }

    init {
        load()
        _viewState.addSource(_book) { mergeSources() }
        _viewState.addSource(_saveInProgress) { mergeSources() }
    }

    fun onSavePressed(book: Book) = viewModelScope.launch {
        try {
            _saveInProgress.postValue(true)
            booksRepository.changeBook(book)
            navigator.goBack(book)
        } catch (e: Exception) {
            if (e !is CancellationException) {
                val message = resources.getString(R.string.error_happened)
                toasts.toast(message)
            }
        } finally {
            _saveInProgress.postValue(false)
        }
    }

    fun onGoToMainPressed(book: Book) = viewModelScope.launch {
        try {
            _saveInProgress.postValue(true)
            booksRepository.changeBook(book)
            navigator.goToMain(book)
        } catch (e: Exception) {
            if (e !is CancellationException) {
                val message = resources.getString(R.string.error_happened)
                toasts.toast(message)
            }
        } finally {
            _saveInProgress.postValue(false)
        }
    }

    fun tryAgain() {
        _book.value = PendingResult()
        load()
    }

    private fun load() {
        into(_book) { booksRepository.getById(screen.bookId) }
    }

    private fun mergeSources() {
        val book = _book.value ?: return
        val saveInProgress = _saveInProgress.value ?: return

        val result = book.map {
            ViewState(
                book = it,
                showSaveButton = !saveInProgress,
                showSaveProgressBar = saveInProgress
            )
        }

        _viewState.value = result
    }

    data class ViewState(
        val book: Book,
        val showSaveButton: Boolean,
        val showSaveProgressBar: Boolean
    )

}