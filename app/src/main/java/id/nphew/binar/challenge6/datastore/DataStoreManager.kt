package id.nphew.binar.challenge6.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(private val context: Context) {

    fun getLoggedInStatus(): Flow<Boolean> {
        return context.dataStore.data.map { pref ->
            pref[KEY_STATUS] ?: false
        }
    }

    fun getUserId(): Flow<Int> {
        return context.dataStore.data.map { pref ->
            pref[KEY_ID] ?: 0
        }
    }

    fun getPassword(): Flow<String> {
        return context.dataStore.data.map { pref ->
            pref[KEY_PASSWORD] ?: "default"
        }
    }

    fun getEmail(): Flow<String> {
        return context.dataStore.data.map { pref ->
            pref[KEY_EMAIL] ?: "default"
        }
    }

    suspend fun setLoggenInStatus(id: Int, username: String, email: String, password: String) {
        context.dataStore.edit { pref ->
            pref[KEY_ID] = id
            pref[KEY_USERNAME] = username
            pref[KEY_EMAIL] = email
            pref[KEY_PASSWORD] = password
            pref[KEY_STATUS] = true
        }
    }

    suspend fun clearUserData() {
        context.dataStore.edit { pref ->
            pref.clear()
        }
    }


    companion object {
        private const val DATA_STORE_NAME = "counter_preference"
        private val KEY_ID = intPreferencesKey("key_id")
        private val KEY_USERNAME = stringPreferencesKey("key_username")
        private val KEY_EMAIL = stringPreferencesKey("key_email")
        private val KEY_PASSWORD = stringPreferencesKey("key_password")
        private val KEY_STATUS = booleanPreferencesKey("key_status")
        private val Context.dataStore by preferencesDataStore(name = DATA_STORE_NAME)
    }
}