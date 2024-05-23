package com.mis.route.newsapp.data.data_sources.di

import com.mis.route.newsapp.data.data_sources.remote.news_api.NewsApiManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataSourcesDiModule {

    @Provides
    fun provideNewsApiManager(): NewsApiManager {
        return NewsApiManager
    }
}