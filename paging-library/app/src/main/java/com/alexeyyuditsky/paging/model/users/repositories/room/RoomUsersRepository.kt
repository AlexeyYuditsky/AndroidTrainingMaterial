package com.alexeyyuditsky.paging.model.users.repositories.room

import android.content.Context
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.alexeyyuditsky.paging.model.users.User
import com.alexeyyuditsky.paging.model.users.UsersPageLoader
import com.alexeyyuditsky.paging.model.users.UsersPagingSource
import com.alexeyyuditsky.paging.model.users.repositories.UsersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

// Схема архитектуры Paging library v3, UsersDao -> PagingSource -> Pager

class RoomUsersRepository(
    private val ioDispatcher: CoroutineDispatcher,
    private val usersDao: UsersDao
) : UsersRepository {

    private val enableErrorsFlow = MutableStateFlow(false)

    override fun isErrorsEnabled(): Flow<Boolean> = enableErrorsFlow

    override fun setErrorsEnabled(value: Boolean) {
        enableErrorsFlow.value = value
    }

    override fun getPagedUsers(searchBy: String): Flow<PagingData<User>> {
        val loader: UsersPageLoader = { pageIndex, pageSize ->
            getUsers(pageIndex, pageSize, searchBy)
        }
        // Pager
        // во-первых его задача принять конфигурацию, т.е. с помощью него мы настраиваем пагинацию списка: сколько элементов загружать за один раз, сколько элементов загружать в первый раз, нужно ли отображать какие-то заглушки placeholder пока элементы не загружены и т.д.
        // во-вторых, его задача принять фабрику PagingSource, т.е. класс Pager будет создавать новый PagingSource, если текущий PagingSource стал невалидным
        // в-третьих, Pager отвечает за логику работы пагинации, за хранение загруженных страниц, за статутсы и т.д.
        // в-четвертых, с помощью Pager мы получаем Flow который мы будем слушать на стороне активити/фрагменты и данные из которого будем отправлять в адаптер списка
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UsersPagingSource(loader, PAGE_SIZE) }
        ).flow
    }

    private suspend fun getUsers(pageIndex: Int, pageSize: Int, searchBy: String): List<User> =
        withContext(ioDispatcher) {

            delay(1000) // some delay to test loading state

            // if "Enable Errors" checkbox is checked -> throw exception
            if (enableErrorsFlow.value) throw IllegalStateException("Error!")

            // calculate offset value required by DAO
            val offset = pageIndex * pageSize

            // get page
            val list = usersDao.getUsers(pageSize, offset, searchBy)

            // map UserDbEntity to User
            return@withContext list.map(UserDbEntity::toUser)
        }

    private companion object {
        const val PAGE_SIZE = 20
    }

}