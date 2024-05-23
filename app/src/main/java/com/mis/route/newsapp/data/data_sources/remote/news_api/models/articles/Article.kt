package com.mis.route.newsapp.data.data_sources.remote.news_api.models.articles

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.mis.route.newsapp.data.data_sources.remote.news_api.models.sources.Source
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(

    @field:SerializedName("publishedAt")
    val publishedAt: String? = null,

    @field:SerializedName("author")
    val author: String? = null,

    @field:SerializedName("urlToImage")
    val urlToImage: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("source")
    val source: Source? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("url")
    val url: String? = null,

    @field:SerializedName("content")
    val content: String? = null
) : Parcelable