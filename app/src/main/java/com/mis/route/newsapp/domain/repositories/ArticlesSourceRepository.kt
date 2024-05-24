package com.mis.route.newsapp.domain.repositories

import com.mis.route.newsapp.data.data_sources.remote.news_api.models.sources.Source

interface ArticlesSourceRepository {
    suspend fun getSources(category: String): List<Source?>?
}