package io.github.amanbutnot.moosic.common

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set

class AppSettings(private val settings: Settings = Settings()) {

    var serverUrl: String
        get() = settings.getString(KEY_SERVER_URL, "")
        set(value) = settings.set(KEY_SERVER_URL, value)

    var username: String
        get() = settings.getString(KEY_USERNAME, "")
        set(value) = settings.set(KEY_USERNAME, value)

    var password: String
        get() = settings.getString(KEY_PASSWORD, "")
        set(value) = settings.set(KEY_PASSWORD, value)

    fun clear() {
        settings.clear()
    }

    companion object {
        private const val KEY_SERVER_URL = "server_url"
        private const val KEY_USERNAME = "username"
        private const val KEY_PASSWORD = "password"
    }
}

val appSettings = AppSettings()
