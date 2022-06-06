package com.alexeyyuditsky.paging.views

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alexeyyuditsky.paging.model.users.User
import com.alexeyyuditsky.paging.model.users.repositories.UsersRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
@FlowPreview
class MainViewModel(
    private val usersRepository: UsersRepository
) : ViewModel() {

    val isErrorsEnabled: Flow<Boolean> = usersRepository.isErrorsEnabled()

    val usersFlow: Flow<PagingData<User>>

    private val searchBy = MutableLiveData("")

    init {
        usersFlow = searchBy.asFlow()
            // if user types text too quickly -> filtering intermediate values to avoid excess loads
            .debounce(500)
            .flatMapLatest {
                // получаем Flow<PagingData<User>> и прослушиваем эти данные на стороне активити/фрагмента и внутри активити/фрагмента отправляем эти данные в адаптер списка UsersAdapter
                usersRepository.getPagedUsers(it)
            }
            // always use cacheIn operator for flows returned by Pager. Otherwise exception may be thrown
            // when 1) refreshing/invalidating or 2) subscribing to the flow more than once.
            .cachedIn(viewModelScope)
    }

    fun setSearchBy(value: String) {
        if (this.searchBy.value == value) return
        this.searchBy.value = value
    }

    fun refresh() {
        this.searchBy.postValue(this.searchBy.value)
    }

    fun setEnableErrors(value: Boolean) {
        // called when 'Enable Errors' checkbox value is changed
        usersRepository.setErrorsEnabled(value)
    }

}