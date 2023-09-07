package com.mis.route.newsapp.webservices

import com.mis.route.newsapp.Constants
import com.mis.route.newsapp.webservices.models.articles.ArticlesResponse
import com.mis.route.newsapp.webservices.models.sources.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface WebServices {
    @GET("v2/top-headlines/sources")
    fun getSources(
        @Query("apiKey") apiKey: String = Constants.API_KEY,
        @Query("category") category: String
    ): Call<SourcesResponse>

    @GET("/v2/everything")
    fun getArticles(
        @Query("apiKey") apiKey: String = Constants.API_KEY,
        @Query("sources") sources: String,
//        @Query("language") language: String,
//        @Query("sortBy") sortBy: String,
        @Query("pageSize") pageSize: Int = 50
    ): Call<ArticlesResponse>
}