package com.mis.route.newsapp.data.data_sources.local.models.articles

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mis.route.newsapp.data.data_sources.local.models.sources.MiniSource
import com.mis.route.newsapp.data.data_sources.remote.news_api.models.articles.Article
import kotlinx.parcelize.Parcelize

@Entity(tableName = "CachedArticle")
@Parcelize
data class CachedArticle(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val publishedAt: String? = null,
    val author: String? = null,
    val urlToImage: String? = null,
    val description: String? = null,
    val source: MiniSource? = null,
    val title: String? = null,
    val url: String? = null,
    val content: String? = null
) : Parcelable {

    fun toArticle(): Article {
        return Article(publishedAt, author, urlToImage, description, source, title, url, content)
    }
}