package com.alexeyyuditsky.paging.model.users

import androidx.paging.Pager
import androidx.paging.PagingSource
import androidx.paging.PagingState

typealias UsersPageLoader = suspend (pageIndex: Int, pageSize: Int) -> List<User>

/**
 * Example implementation of [PagingSource].
 * It is used by [Pager] for fetching data.
 */
// Класс отвечает за загрузку данных
// PagingSource<Int, User>, Int - какие аргументы пагинации мы используем (представляет индекс элемента с которого мы начинаем загрузку или индекс страницы с которой начинаем загрузку
// User - с какими данными мы работаем
// Источником данных для PagingSource может быть, что угодно(бд, сеть, файл и т.д.)
class UsersPagingSource(
    private val loader: UsersPageLoader,
    private val pageSize: Int
) : PagingSource<Int, User>() {

    // метод отвечает за загрузку данных
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        // get the index of page to be loaded (it may be NULL, in this case let's load the first page with index = 0)
        val pageIndex = params.key ?: 0 // params.key - null, 3, 4, 5 и т.д., params.loadSize - 60, 20, 20, 20 и т.д.

        return try {
            // loading the desired page of users
            val users = loader(pageIndex, params.loadSize)

            // success! now we can return LoadResult.Page
            return LoadResult.Page(
                data = users,
                // index of the previous page if exists
                prevKey = if (pageIndex == 0) null else pageIndex - 1,
                // index of the next page if exists;
                // please note that 'params.loadSize' may be larger for the first load (by default x3 times)
                nextKey = if (users.size == params.loadSize) pageIndex + (params.loadSize / pageSize) else null
            )
        } catch (e: Exception) {
            // failed to load users -> need to return LoadResult.Error
            LoadResult.Error(throwable = e)
        }
    }

    // метод отвечает за вычисление аргументов пагинации в случае, если текущее состояние списка стало невалидыным, например: пользователь запросил обновление данных с нуля
    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        // get the most recently accessed index in the users list:
        val anchorPosition = state.anchorPosition ?: return null
        // convert item index to page index:
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        // page doesn't have 'currentKey' property, so need to calculate it manually:
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

}