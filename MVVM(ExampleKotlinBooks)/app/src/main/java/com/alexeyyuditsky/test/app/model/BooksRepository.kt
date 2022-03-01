package com.alexeyyuditsky.test.app.model

import com.alexeyyuditsky.test.foundation.model.Repository

typealias BooksListener = (List<Book>) -> Unit

interface BooksRepository : Repository {

    suspend fun getBooks(): List<Book>

    suspend fun getById(id: Long): Book

    suspend fun changeBook(book: Book)

    fun addListener(listener: BooksListener)

    fun removeListener(listener: BooksListener)

}