package com.alexeyyuditsky.test.app.model

import com.alexeyyuditsky.test.foundation.model.Repository

typealias BooksListener = (List<Book>) -> Unit

interface BooksRepository : Repository {

    fun getById(id: Long): Book

    fun changeBook(book: Book)

    fun addListener(listener: BooksListener)

    fun removeListener(listener: BooksListener)

}