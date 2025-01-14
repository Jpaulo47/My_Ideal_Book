package com.joaorodrigues.myidealbook.data.local.cache

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore(name = "user_preferences")

class PreferencesManager(private val context: Context) {

    private val LAST_SEARCH_KEY = stringPreferencesKey("last_search_query")
    private val DEFAULT_SEARCH_QUERY = "livros"

    suspend fun getLastSearchQuery(): String {
        val preferences = context.dataStore.data.first()
        return preferences[LAST_SEARCH_KEY] ?: DEFAULT_SEARCH_QUERY
    }

    suspend fun saveLastSearchQuery(query: String) {
        context.dataStore.edit { preferences ->
            preferences[LAST_SEARCH_KEY] = query
        }
    }
}
