package com.mis.route.newsapp.data.repositories

import android.content.Context
import com.mis.route.newsapp.data.data_sources.local.NewsDatabase
import com.mis.route.newsapp.data.data_sources.local.models.sources.CachedSource
import com.mis.route.newsapp.data.data_sources.remote.news_api.NewsApiManager
import com.mis.route.newsapp.data.data_sources.remote.news_api.models.sources.Source
import com.mis.route.newsapp.domain.repositories.ArticlesSourceRepository
import com.mis.route.newsapp.presentations.utils.ConnectivityChecker
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ArticlesSourceRepositoryImpl @Inject constructor(
    private val newsApiManager: NewsApiManager,
    private val connectivityChecker: ConnectivityChecker,
    @ApplicationContext private val context: Context
) : ArticlesSourceRepository {

    override suspend fun getSources(category: String): List<Source?> {
        return if (connectivityChecker.isNetworkAvailable()) {
            val response = newsApiManager.getApi().getSources(category = category)
            val cachedSources = response.sources?.map { it?.toCachedSource() }
            val nonNullList = cachedSources?.filterNotNull() as List<CachedSource>
            NewsDatabase.getDatabase(context).sourceDao().saveSources(nonNullList)
            response.sources
        } else {
            val cachedSourcesList =
                NewsDatabase.getDatabase(context).sourceDao().getSources(category)
            cachedSourcesList.map { it.toSource() }
        }
    }
}