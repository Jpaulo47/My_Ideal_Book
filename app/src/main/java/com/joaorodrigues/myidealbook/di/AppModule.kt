package com.joaorodrigues.myidealbook.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.joaorodrigues.myidealbook.util.Constants.BASE_URL
import com.joaorodrigues.myidealbook.util.Constants.NEWS_DATABASE_NAME
import com.joaorodrigues.myidealbook.data.local.BooksDao
import com.joaorodrigues.myidealbook.data.local.BooksDatabase
import com.joaorodrigues.myidealbook.data.local.cache.PreferencesManager
import com.joaorodrigues.myidealbook.data.remote.BooksApi
import com.joaorodrigues.myidealbook.data.repository.BooksRepositoryImpl
import com.joaorodrigues.myidealbook.domain.repository.BooksRepository
import com.joaorodrigues.myidealbook.domain.usecases.BooksUseCases
import com.joaorodrigues.myidealbook.domain.usecases.DeleteBook
import com.joaorodrigues.myidealbook.domain.usecases.ExistsBook
import com.joaorodrigues.myidealbook.domain.usecases.GetBooks
import com.joaorodrigues.myidealbook.domain.usecases.SelectBook
import com.joaorodrigues.myidealbook.domain.usecases.SelectBooks
import com.joaorodrigues.myidealbook.domain.usecases.UpsertBook
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNewsApi(): BooksApi {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build().create(BooksApi::class.java)
    }

    @Provides
    @Singleton
    fun provideBooksRepository(
        booksApi: BooksApi,
        booksDao: BooksDao
    ): BooksRepository = BooksRepositoryImpl(booksApi, booksDao)

    @Provides
    @Singleton
    fun provideBooksUseCase(
        booksRepository: BooksRepository
    ): BooksUseCases {
        return BooksUseCases(
            getBooks = GetBooks(booksRepository),
            upsertBook = UpsertBook(booksRepository),
            deleteBook = DeleteBook(booksRepository),
            selectBook = SelectBook(booksRepository),
            selectBooks = SelectBooks(booksRepository),
            existsBook = ExistsBook(booksRepository)
        )
    }

    @Provides
    @Singleton
    fun provideBooksDatabase(
        application: Application
    ): BooksDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = BooksDatabase::class.java,
            name = NEWS_DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideBooksDao(
        booksDatabase: BooksDatabase
    ): BooksDao = booksDatabase.booksDao

    @Provides
    @Singleton
    fun providePreferencesManager(@ApplicationContext context: Context): PreferencesManager {
        return PreferencesManager(context)
    }

}
