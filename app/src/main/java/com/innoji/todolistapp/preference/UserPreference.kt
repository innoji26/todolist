package com.innoji.todolistapp.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.innoji.todolistapp.data.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference private constructor(private val dataStore: DataStore<Preferences>){
    fun getUser(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[USERNAME_KEY] ?: "",
                preferences[STATE_KEY] ?: false,
                preferences[ACCESSTOKEN_KEY] ?: "",
            )
        }
    }

    suspend fun login(user: UserModel){
        dataStore.edit { preferences ->
            preferences[USERNAME_KEY] = user.username
            preferences[STATE_KEY] = user.isLogin
            preferences[ACCESSTOKEN_KEY] = user.accessToken
        }
    }

    suspend fun logout(){
        dataStore.edit { preferences ->
            preferences[USERNAME_KEY] = ""
            preferences[STATE_KEY] = false
            preferences[ACCESSTOKEN_KEY] = ""
        }
    }

    companion object{
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val USERNAME_KEY = stringPreferencesKey("username")
        private val ACCESSTOKEN_KEY = stringPreferencesKey("accessToken")
        private val STATE_KEY = booleanPreferencesKey("isLogin")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference{
            return INSTANCE ?: synchronized(this){
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}