package com.dicoding.nav_capstone.data.local.preferences

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.dicoding.nav_capstone.data.local.model.SessionModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "session")

class SessionPreferences private constructor(private val dataStore: DataStore<Preferences>) {
    companion object {
        @Volatile
        private var INSTANCE: SessionPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SessionPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SessionPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

    private val TOKEN_KEY = stringPreferencesKey("token")
    private val IS_LOGIN = booleanPreferencesKey("isLogin")
    private val EMAIL_KEY = stringPreferencesKey("email")

    fun getSession(): Flow<SessionModel> {
        return dataStore.data.map { session ->
            SessionModel(
                session[TOKEN_KEY] ?: "",
                session[IS_LOGIN] ?: false,
                session[EMAIL_KEY] ?: ""
            )
        }
    }

    suspend fun saveSession(sessionModel: SessionModel) {
        dataStore.edit { session ->
            session[TOKEN_KEY] = sessionModel.token
            session[IS_LOGIN] = sessionModel.isLogin
            session[EMAIL_KEY] = sessionModel.email
        }
    }

    suspend fun logOut() {
        try {
            dataStore.edit { session ->
                session.clear()
            }
        } catch (e: Exception) {
            Log.e("SessionPreferences", "Error during logout", e)
        }
    }
}