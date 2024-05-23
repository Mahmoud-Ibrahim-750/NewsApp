package com.mis.route.newsapp.data.data_sources.local.models.sources

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MiniSource(
    val id: String,
    val name: String
) : Parcelable
