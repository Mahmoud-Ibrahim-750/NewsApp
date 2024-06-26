package com.mis.route.newsapp.data.data_sources.remote.news_api

import com.mis.route.newsapp.data.data_sources.remote.news_api.models.articles.ArticlesResponse
import com.mis.route.newsapp.data.data_sources.remote.news_api.models.sources.SourcesResponse
import com.mis.route.newsapp.presentations.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsWebServices {
    @GET("v2/top-headlines/sources")
    suspend fun getSources(
        @Query("apiKey") apiKey: String = Constants.API_KEY,
        @Query("category") category: String
    ): SourcesResponse

    @GET("/v2/everything")
    suspend fun getArticles(
        @Query("apiKey") apiKey: String = Constants.API_KEY,
        @Query("sources") sources: String,
        @Query("q") query: String? = null,
//        @Query("language") language: String,
//        @Query("sortBy") sortBy: String,
        @Query("pageSize") pageSize: Int = Constants.PAGE_SIZE,
        @Query("page") page: Int = 1
    ): ArticlesResponse
}