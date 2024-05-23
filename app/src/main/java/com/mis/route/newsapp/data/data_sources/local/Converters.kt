package com.mis.route.newsapp.data.data_sources.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.mis.route.newsapp.data.data_sources.local.models.sources.MiniSource


class Converters {
    @TypeConverter
    fun toString(source: MiniSource): String {
        return Gson().toJson(source)
    }

    @TypeConverter
    fun fromString(jsonString: String): MiniSource {
        return Gson().fromJson(jsonString, MiniSource::class.java)
    }
}