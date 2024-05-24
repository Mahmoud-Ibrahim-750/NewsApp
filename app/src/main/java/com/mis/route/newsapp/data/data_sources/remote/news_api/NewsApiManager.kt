package com.mis.route.newsapp.data.data_sources.remote.news_api

import com.mis.route.newsapp.presentations.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsApiManager {
    private var retrofit: Retrofit? = null

    private fun getInstance(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(Constants.NEWS_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }

    fun getApi(): NewsWebServices = getInstance().create(NewsWebServices::class.java)
}