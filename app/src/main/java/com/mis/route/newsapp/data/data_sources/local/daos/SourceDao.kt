package com.mis.route.newsapp.data.data_sources.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mis.route.newsapp.data.data_sources.local.models.sources.CachedSource

@Dao
interface SourceDao {

    @Query("select * from cachedsource where category = :category")
    fun getSources(category: String): List<CachedSource>

    @Insert
    fun saveSources(sourceList: List<CachedSource>)
}