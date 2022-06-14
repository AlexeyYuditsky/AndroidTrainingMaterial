package com.alexeyyuditsky.test.model.employees.room

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.alexeyyuditsky.test.model.employees.EmployeesPageLoader
import com.alexeyyuditsky.test.model.employees.EmployeesPagingSource
import com.alexeyyuditsky.test.model.employees.EmployeesRepository
import com.alexeyyuditsky.test.model.employees.entities.Employee
import com.alexeyyuditsky.test.model.employees.room.entities.EmployeeDbEntity
import com.github.javafaker.Faker
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

class RoomEmployeesRepository(
    private val employeesDao: EmployeesDao,
    private val ioDispatcher: CoroutineDispatcher
) : EmployeesRepository {

    override val enableErrorFlow = MutableStateFlow(false)

    override suspend fun initDatabaseIfEmpty() = withContext(ioDispatcher) {
        if (employeesDao.getEmployees().isEmpty()) {
            val faker = Faker()
            val employeesList = mutableListOf<EmployeeDbEntity>()
            repeat(DATABASE_SIZE) {
                employeesList.add(
                    EmployeeDbEntity(
                        id = 0,
                        image = IMAGE_LIST[it % IMAGE_LIST.size],
                        name = faker.name().fullName(),
                        nation = faker.nation().nationality(),
                        email = faker.internet().emailAddress(),
                        age = faker.random().nextInt(16, 65)
                    )
                )
            }
            employeesDao.insertEmployeesList(employeesList)
        }
    }

    override fun setErrorEnabled(value: Boolean) {
        enableErrorFlow.value = value
    }

    override fun getPagedEmployees(): Flow<PagingData<Employee>> {
        val loader: EmployeesPageLoader = { pageIndex, pageSize ->
            getEmployees(pageIndex, pageSize)
        }
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { EmployeesPagingSource(loader, PAGE_SIZE) }
        ).flow
    }

    private suspend fun getEmployees(pageIndex: Int, pageSize: Int): List<Employee> = withContext(ioDispatcher) {
        delay(2000)
        if (enableErrorFlow.value) throw IllegalStateException("Error!")
        val offset = pageIndex * pageSize
        val list = employeesDao.getEmployees(pageSize, offset)
        return@withContext list.map(EmployeeDbEntity::toEmployee)
    }

    companion object {
        const val DATABASE_SIZE = 1000
        private const val PAGE_SIZE = 15
        private val IMAGE_LIST = listOf(
            "https://images.unsplash.com/photo-1438761681033-6461ffad8d80?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8cGVyc29ufGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60",
            "https://images.unsplash.com/photo-1544005313-94ddf0286df2?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8cGVyc29ufGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60",
            "https://images.unsplash.com/flagged/photo-1570612861542-284f4c12e75f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8cGVyc29ufGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60",
            "https://images.unsplash.com/photo-1547425260-76bcadfb4f2c?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NXx8cGVyc29ufGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60",
            "https://images.unsplash.com/photo-1552058544-f2b08422138a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Nnx8cGVyc29ufGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60",
            "https://images.unsplash.com/photo-1499952127939-9bbf5af6c51c?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTF8fHBlcnNvbnxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60",
            "https://images.unsplash.com/photo-1491349174775-aaafddd81942?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTB8fHBlcnNvbnxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60",
            "https://images.unsplash.com/photo-1504593811423-6dd665756598?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTZ8fHBlcnNvbnxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60",
            "https://images.unsplash.com/photo-1531427186611-ecfd6d936c79?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MjF8fHBlcnNvbnxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60",
            "https://images.unsplash.com/photo-1599566150163-29194dcaad36?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTh8fHBlcnNvbnxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60"
        )
    }

}