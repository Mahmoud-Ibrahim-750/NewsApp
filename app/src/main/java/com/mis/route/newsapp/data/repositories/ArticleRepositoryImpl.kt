package com.mis.route.newsapp.data.repositories

import android.content.Context
import com.mis.route.newsapp.ConnectivityChecker
import com.mis.route.newsapp.data.data_sources.local.NewsDatabase
import com.mis.route.newsapp.data.data_sources.local.models.sources.MiniSource
import com.mis.route.newsapp.data.data_sources.remote.news_api.NewsApiManager
import com.mis.route.newsapp.data.data_sources.remote.news_api.models.articles.Article
import com.mis.route.newsapp.domain.repositories.ArticleRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val newsApiManager: NewsApiManager,
    private val connectivityChecker: ConnectivityChecker,
    @ApplicationContext private val context: Context
) : ArticleRepository {

    override suspend fun getArticle(source: MiniSource, query: String?): List<Article?> {
        return if (connectivityChecker.isNetworkAvailable()) {
            val response = newsApiManager.getApi().getArticles(sources = source.id, query = query)
            val cachedArticle = response.articles?.map { it?.toCachedArticle() }
            val nonNullList = cachedArticle?.filterNotNull() ?: emptyList()
            NewsDatabase.getDatabase(context).articleDao().saveArticles(nonNullList)
            response.articles ?: emptyList()
        } else {
            val cachedArticleList =
                NewsDatabase.getDatabase(context).articleDao().getArticles(source)
            cachedArticleList.map { it.toArticle() }
        }
    }
}
