package com.alexeyyuditsky.test.app.model

import com.alexeyyuditsky.test.foundation.model.Repository
import com.alexeyyuditsky.test.foundation.model.tasks.Task

typealias BooksListener = (List<Book>) -> Unit

interface BooksRepository : Repository {

    fun getBooks(): Task<List<Book>>

    fun getById(id: Long): Task<Book>

    fun changeBook(book: Book):Task<Unit>

    fun addListener(listener: BooksListener)

    fun removeListener(listener: BooksListener)

}