package com.alexeyyuditsky.test.app.view.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.app.model.Book
import com.alexeyyuditsky.test.app.model.BooksRepository
import com.alexeyyuditsky.test.foundation.model.*
import com.alexeyyuditsky.test.foundation.views.BaseViewModel
import com.alexeyyuditsky.test.foundation.sideeffects.navigator.Navigator
import com.alexeyyuditsky.test.foundation.sideeffects.resources.Resources
import com.alexeyyuditsky.test.foundation.sideeffects.toasts.Toasts
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class EditBookViewModel(
    private val screen: EditBookFragment.Screen,
    private val navigator: Navigator,
    private val toasts: Toasts,
    private val resources: Resources,
    private val booksRepository: BooksRepository,
) : BaseViewModel() {

    private val _book = MutableStateFlow<Result<Book>>(PendingResult())
    private val _saveInProgress = MutableStateFlow<Progress>(EmptyProgress)

    val viewState: Flow<Result<ViewState>> = combine(
        _book,
        _saveInProgress,
        ::mergeSources
    )

    val screenTitle: LiveData<String> = viewState.map { result ->
        if (result is SuccessResult) {
            resources.getString(
                R.string.view_model_description_fragment_title,
                result.data.book.name
            )
        } else {
            resources.getString(R.string.book)
        }
    }.asLiveData()

    init {
        load()
    }

    fun onSavePressed(book: Book, goToMainScreen: Boolean = false) = viewModelScope.launch {
        try {
            _saveInProgress.value = PercentageProgress.START

            booksRepository.changeBook(book).collect { percentage ->
                _saveInProgress.value = PercentageProgress(percentage)
            }

            if (goToMainScreen)
                navigator.goToMainScreen()
            else
                navigator.goBack(book)

        } catch (e: Exception) {
            if (e !is CancellationException) {
                val message = resources.getString(R.string.error_happened)
                toasts.toast(message)
            }
        } finally {
            _saveInProgress.value = EmptyProgress
        }
    }

    fun tryAgain() {
        _book.value = PendingResult()
        load()
    }

    private fun load() {
        into(_book) { booksRepository.getById(screen.bookId) }
    }

    private fun mergeSources(book: Result<Book>, saveInProgress: Progress): Result<ViewState> {
        return book.map {
            ViewState(
                book = it,
                showSaveButton = !saveInProgress.isInProgress(),
                showSaveProgressBar = saveInProgress.isInProgress(),
                saveProgressPercentage = saveInProgress.getPercentage(),
                saveProgressPercentageMessage = resources.getString(
                    R.string.percentage_value,
                    saveInProgress.getPercentage()
                )
            )
        }
    }

    data class ViewState(
        val book: Book,
        val showSaveButton: Boolean,
        val showSaveProgressBar: Boolean,
        val saveProgressPercentage: Int,
        val saveProgressPercentageMessage: String,
    )

}