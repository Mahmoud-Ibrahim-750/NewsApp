package com.mis.route.newsapp.webservices.models.articles

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.mis.route.newsapp.webservices.models.sources.Source
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArticlesResponse(

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("totalResults")
    val totalResults: Int? = null,

    @field:SerializedName("articles")
    val articles: List<Article?>? = null,

    @field:SerializedName("code")
    val code: String? = null,

    @field:SerializedName("message")
    val message: List<Source?>? = null
) : Parcelable