package ru.topbun.data.source.local.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

typealias Settings = DataStore<Preferences>

object AppSettings {

    private const val FILE_NAME = "settings"

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = FILE_NAME)

    val KEY_TOKEN = stringPreferencesKey(name = "token")

}