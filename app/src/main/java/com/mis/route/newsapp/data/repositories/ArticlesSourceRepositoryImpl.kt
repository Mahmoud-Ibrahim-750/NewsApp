package com.mis.route.newsapp.data.repositories

import com.mis.route.newsapp.data.data_sources.remote.news_api.NewsApiManager
import com.mis.route.newsapp.data.data_sources.remote.news_api.models.sources.Source
import com.mis.route.newsapp.domain.repositories.ArticlesSourceRepository
import javax.inject.Inject

class ArticlesSourceRepositoryImpl @Inject constructor(
    private val newsApiManager: NewsApiManager
) : ArticlesSourceRepository {

    override suspend fun getSources(category: String): List<Source?>? {
        val response = newsApiManager.getApi().getSources(category = category)
        return response.sources
    }
}