package com.mis.route.newsapp.data.data_sources.remote.news_api.models.sources

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.mis.route.newsapp.data.data_sources.local.models.sources.CachedSource
import com.mis.route.newsapp.data.data_sources.local.models.sources.MiniSource
import kotlinx.parcelize.Parcelize

@Parcelize
data class Source(
    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("country")
    val country: String? = null,

    @field:SerializedName("url")
    val url: String? = null,

    @field:SerializedName("language")
    val language: String? = null,

    @field:SerializedName("category")
    val category: String? = null
) : Parcelable {

    fun toCachedSource(): CachedSource {
        return CachedSource(0, id, name, description, country, url, language, category)
    }

    fun toMiniSource(): MiniSource {
        return MiniSource(id ?: "", name ?: "")
    }
}