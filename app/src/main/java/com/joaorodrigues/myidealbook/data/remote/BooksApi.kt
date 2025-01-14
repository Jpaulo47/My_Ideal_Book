package com.joaorodrigues.myidealbook.data.remote

import com.joaorodrigues.myidealbook.BuildConfig
import com.joaorodrigues.myidealbook.data.remote.dto.BooksResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BooksApi {
    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("startIndex") startIndex: Int = 0,
        @Query("maxResults") maxResults: Int = 40,
        @Query("printType ") printType : String = "books",
        @Query("key") apiKey: String = BuildConfig.apiKey
    ): BooksResponse
}