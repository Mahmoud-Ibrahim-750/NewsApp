package com.mis.route.newsapp.webservices.models.sources

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SourcesResponse(
    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("sources")
    val sources: List<Source?>? = null,

    @field:SerializedName("code")
    val code: String? = null,

    @field:SerializedName("message")
    val message: List<Source?>? = null
) : Parcelable