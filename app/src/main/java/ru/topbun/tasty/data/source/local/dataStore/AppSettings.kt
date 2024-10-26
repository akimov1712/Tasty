package ru.topbun.tasty.data.source.local.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.SynchronizedObject
import okio.Path.Companion.toPath

typealias Settings = DataStore<Preferences>

object AppSettings {

    private const val FILE_NAME = "settings"

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = FILE_NAME)

    val KEY_TOKEN = stringPreferencesKey(name = "token")

}