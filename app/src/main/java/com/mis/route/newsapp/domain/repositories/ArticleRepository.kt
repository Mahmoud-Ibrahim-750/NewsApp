package com.mis.route.newsapp.domain.repositories

import com.mis.route.newsapp.data.data_sources.local.models.sources.MiniSource
import com.mis.route.newsapp.data.data_sources.remote.news_api.models.articles.Article

interface ArticleRepository {
    suspend fun getArticle(source: MiniSource, query: String? = null, page: Int): List<Article?>?
}