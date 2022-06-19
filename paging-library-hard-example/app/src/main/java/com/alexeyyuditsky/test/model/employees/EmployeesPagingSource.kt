package com.alexeyyuditsky.test.model.employees

import androidx.paging.PagingSource
import androidx.paging.PagingState

typealias EmployeesPageLoader = suspend (pageIndex: Int, pageSize: Int) -> List<Employee>

class EmployeesPagingSource(
    private val loader: EmployeesPageLoader,
    private val pageSize: Int
) : PagingSource<Int, Employee>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Employee> {
        val pageIndex = params.key ?: 0

        return try {
            val employees = loader(pageIndex, params.loadSize)
            return LoadResult.Page(
                data = employees,
                prevKey = if (pageIndex == 0) null else pageIndex - 1,
                nextKey = if (employees.size == params.loadSize) pageIndex + (params.loadSize / pageSize) else null
            )
        } catch (e: Exception) {
            LoadResult.Error(throwable = e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Employee>): Int = 0

}