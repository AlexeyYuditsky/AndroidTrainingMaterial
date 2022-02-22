package com.alexeyyuditsky.test.app.view.edit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.app.model.Book
import com.alexeyyuditsky.test.app.model.BooksRepository
import com.alexeyyuditsky.test.foundation.model.ErrorResult
import com.alexeyyuditsky.test.foundation.model.FinalResult
import com.alexeyyuditsky.test.foundation.model.PendingResult
import com.alexeyyuditsky.test.foundation.model.SuccessResult
import com.alexeyyuditsky.test.foundation.model.tasks.TasksFactory
import com.alexeyyuditsky.test.foundation.navigator.Navigator
import com.alexeyyuditsky.test.foundation.uiactions.UIActions
import com.alexeyyuditsky.test.foundation.views.BaseViewModel
import com.alexeyyuditsky.test.foundation.views.LiveResult
import com.alexeyyuditsky.test.foundation.views.MutableLiveResult

class EditBookViewModel(
    private val screen: EditBookFragment.Screen,
    private val navigator: Navigator,
    private val uiActions: UIActions,
    private val booksRepository: BooksRepository,
    private val tasksFactory: TasksFactory,
) : BaseViewModel() {

    private val _book = MutableLiveResult<Book>(PendingResult())
    val book: LiveResult<Book> = _book

    val screenTitle: LiveData<String> = Transformations.map(_book) { result ->
        if (result is SuccessResult) {
            uiActions.getString(R.string.view_model_description_fragment_title, result.data.name)
        } else {
            uiActions.getString(R.string.book)
        }
    }

    init {
        load()
    }

    fun onSavePressed(book: Book) {
        tasksFactory.async {
            booksRepository.changeBook(book).await()
            return@async book
        }
            .safeEnqueue(::onSaved)
    }

    fun onGoToMainPressed(book: Book) {
        tasksFactory.async {
            booksRepository.changeBook(book).await()
            return@async book
        }
            .safeEnqueue(::onSaved2)
    }

    private fun load() {
        booksRepository.getById(screen.bookId).into(_book)
    }

    private fun onSaved(result: FinalResult<Book>) {
        when (result) {
            is SuccessResult -> navigator.goBack(result.data)
            is ErrorResult -> uiActions.toast(uiActions.getString(R.string.error_happened))
        }
    }

    private fun onSaved2(result: FinalResult<Book>) {
        when (result) {
            is SuccessResult -> {
                navigator.goToMain()
                uiActions.toast(uiActions.getString(R.string.changes_saved))
            }
            is ErrorResult -> uiActions.toast(uiActions.getString(R.string.error_happened))
        }
    }

}