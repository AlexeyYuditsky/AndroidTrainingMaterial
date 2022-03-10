package com.alexeyyuditsky.test.app.model

import com.alexeyyuditsky.test.foundation.model.Repository
import kotlinx.coroutines.flow.Flow

typealias BooksListener = (List<Book>) -> Unit

interface BooksRepository : Repository {

    suspend fun getBooks(): List<Book>

    suspend fun getById(id: Long): Book

    fun changeBook(book: Book): Flow<Int>

    fun listenBooksList(): Flow<List<Book>>

}