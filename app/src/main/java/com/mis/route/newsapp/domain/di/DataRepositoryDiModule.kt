package com.mis.route.newsapp.domain.di

import com.mis.route.newsapp.data.repositories.ArticlesSourceRepositoryImpl
import com.mis.route.newsapp.domain.repositories.ArticlesSourceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataRepositoryDiModule {
    @Binds
    fun provideArticlesSourceRepository(
        articlesSourceRepositoryImpl: ArticlesSourceRepositoryImpl
    ): ArticlesSourceRepository
}