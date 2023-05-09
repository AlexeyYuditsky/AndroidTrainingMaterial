package com.alexeyyuditsky.news.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.alexeyyuditsky.news.data.retrofit.Article

@Database(
    version = 1,
    entities = [Article::class],
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao

}