package com.mis.route.newsapp.data.data_sources.local.models.sources

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mis.route.newsapp.data.data_sources.remote.news_api.models.sources.Source
import kotlinx.parcelize.Parcelize

@Entity(tableName = "CachedSource")
@Parcelize
data class CachedSource(
    @PrimaryKey(autoGenerate = true)
    val pk: Int,
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val country: String? = null,
    val url: String? = null,
    val language: String? = null,
    val category: String? = null
) : Parcelable {

    fun toSource(): Source {
        return Source(id, name, description, country, url, language, category)
    }

    fun toMiniSource(): MiniSource {
        return MiniSource(id ?: "", name ?: "")
    }
}
