package com.mis.route.newsapp.data.data_sources.remote.news_api

import com.mis.route.newsapp.Constants
import com.mis.route.newsapp.data.data_sources.remote.news_api.models.articles.ArticlesResponse
import com.mis.route.newsapp.data.data_sources.remote.news_api.models.sources.SourcesResponse
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
//        @Query("language") language: String,
//        @Query("sortBy") sortBy: String,
        @Query("pageSize") pageSize: Int = Constants.PAGE_SIZE
    ): ArticlesResponse
}