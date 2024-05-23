package com.mis.route.newsapp.data.data_sources.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mis.route.newsapp.data.data_sources.local.models.articles.CachedArticle
import com.mis.route.newsapp.data.data_sources.local.models.sources.MiniSource

@Dao
interface ArticleDao {

    @Query("select * from cachedarticle where source = :source")
    fun getArticles(source: MiniSource): List<CachedArticle>

    @Insert
    fun saveArticles(articlesList: List<CachedArticle>)
}