package ru.topbun.data.source.local.dataStore

import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import ru.topbun.android.FailedExtractTokenException

suspend fun Settings.getToken(): String {
    val token = this.data
        .map { it[AppSettings.KEY_TOKEN] }
        .firstOrNull()
    return token ?: throw ru.topbun.android.FailedExtractTokenException()
}

suspend fun Settings.saveToken(token: String){
    this.edit {
        it[AppSettings.KEY_TOKEN] = token
    }
}