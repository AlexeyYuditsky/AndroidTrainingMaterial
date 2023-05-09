package com.alexeyyuditsky.news.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alexeyyuditsky.news.data.NETWORK_PAGE_SIZE
import com.alexeyyuditsky.news.data.retrofit.Article
import com.alexeyyuditsky.news.data.retrofit.NewsApi
import retrofit2.HttpException

private const val NEWS_STARTING_PAGE_INDEX = 1

class PagingNewsSource(
    private val newsApi: NewsApi,
    private val query: String
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val position = params.key ?: NEWS_STARTING_PAGE_INDEX

        return try {
            val response = newsApi.searchForNews(query, position, params.loadSize)
            val articles = response.articles
            val nextKey = if (articles.isEmpty()) null else position + params.loadSize / NETWORK_PAGE_SIZE
            LoadResult.Page(
                data = articles,
                prevKey = if (position == NEWS_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}