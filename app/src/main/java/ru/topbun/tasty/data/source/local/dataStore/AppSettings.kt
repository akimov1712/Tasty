package ru.topbun.tasty.data.source.local.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.SynchronizedObject
import okio.Path.Companion.toPath

typealias Settings = DataStore<Preferences>

object AppSettings {

    private const val FILE_NAME = "settings.preferences_pb"

    @OptIn(InternalCoroutinesApi::class)
    private val lock = SynchronizedObject()
    private lateinit var dataStore: Settings

    private fun getDataStore(producePath: () -> String): Settings{
        return synchronized(lock){
            if (AppSettings::dataStore.isInitialized) dataStore
            else PreferenceDataStoreFactory.createWithPath {
                producePath().toPath()
            }.also { dataStore = it }
        }
    }

    fun createDataStore(context: Context): Settings {
        return getDataStore{
            context.filesDir
                .resolve(FILE_NAME)
                .absolutePath
        }
    }

    val KEY_TOKEN = stringPreferencesKey(name = "token")

}