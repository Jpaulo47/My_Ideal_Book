package com.joaorodrigues.myidealbook.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.joaorodrigues.myidealbook.domain.model.BookModel

@Database(entities = [BookModel::class], version = 1)
@TypeConverters(BooksTypeConversor::class)
abstract class BooksDatabase: RoomDatabase() {
    abstract val booksDao: BooksDao
}