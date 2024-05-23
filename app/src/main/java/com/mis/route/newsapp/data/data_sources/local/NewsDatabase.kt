package com.mis.route.newsapp.data.data_sources.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mis.route.newsapp.Constants
import com.mis.route.newsapp.data.data_sources.local.daos.ArticleDao
import com.mis.route.newsapp.data.data_sources.local.daos.SourceDao
import com.mis.route.newsapp.data.data_sources.local.models.articles.CachedArticle
import com.mis.route.newsapp.data.data_sources.local.models.sources.CachedSource

@Database(entities = [CachedSource::class, CachedArticle::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun sourceDao(): SourceDao
    abstract fun articleDao(): ArticleDao

    companion object {
        @Volatile
        private var db: NewsDatabase? = null

        fun getDatabase(context: Context): NewsDatabase =
            db ?: synchronized(this) {
                db = Room.databaseBuilder(
                    context,
                    NewsDatabase::class.java,
                    Constants.ROOM_DATABASE_FILE_NAME
                ).build()
                db!!
            }
    }
}