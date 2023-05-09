package com.alexeyyuditsky.news.presentation.main.breaking

import androidx.lifecycle.viewModelScope
import com.alexeyyuditsky.news.data.retrofit.NewsResponse
import com.alexeyyuditsky.news.domain.NewsRepository
import com.alexeyyuditsky.news.presentation.base.BaseViewModel
import com.alexeyyuditsky.news.presentation.base.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.Response

class BreakingViewModel(
    private val newsRepository: NewsRepository
) : BaseViewModel() {

    private val _breakingNews = MutableStateFlow<Result<NewsResponse>>(Result.Loading())
    val breakingNews = _breakingNews.asStateFlow()

    var breakingNewsPage = 1
    private var breakingNewsResponse: NewsResponse? = null

    init {
        getBreakingNews()
    }

    fun getBreakingNews(countryCode: String = "us") = viewModelScope.launch {
        safeBreakingNewsCall(countryCode)
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Result<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                breakingNewsPage++
                if (breakingNewsResponse == null) {
                    breakingNewsResponse = resultResponse
                } else {
                    breakingNewsResponse?.articles?.addAll(resultResponse.articles)
                }
                return Result.Success(breakingNewsResponse!!)
            }
        }
        return Result.Error(response.message())
    }

    private suspend fun safeBreakingNewsCall(countryCode: String) {
        try {
            val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
            val handledResponse = handleBreakingNewsResponse(response)
            _breakingNews.emit(handledResponse)
        } catch (t: Throwable) {
            when (t) {
                is IOException -> _breakingNews.emit(Result.Error("Network failure"))
                else -> _breakingNews.emit(Result.Error("Conversion error"))
            }
        }
    }

}