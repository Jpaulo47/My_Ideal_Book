package com.joaorodrigues.myidealbook.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.joaorodrigues.myidealbook.domain.model.BookModel
import kotlinx.coroutines.flow.Flow

@Dao
interface BooksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(book: BookModel)

    @Delete
    suspend fun delete(book: BookModel)

    @Query("SELECT * FROM BookModel")
    fun getBooks(): Flow<List<BookModel>>

    @Query("SELECT * FROM BookModel WHERE id=:id")
    suspend fun getBook(id: String): BookModel?

    @Query("SELECT EXISTS(SELECT 1 FROM BookModel WHERE id = :id)")
    suspend fun existsBook(id: String): Boolean
}