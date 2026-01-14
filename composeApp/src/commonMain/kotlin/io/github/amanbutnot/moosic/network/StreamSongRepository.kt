package io.github.amanbutnot.moosic.network

import io.github.amanbutnot.moosic.common.appSettings
import io.github.amanbutnot.moosic.common.httpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class StreamSongRepository {
    suspend fun streamSong(id: String): ByteArray? {
        val baseUrl = appSettings.serverUrl
        val url = if (baseUrl.endsWith("/")) "${baseUrl}rest/stream.view" else "${baseUrl}/rest/stream.view"

        return try {
            val response = httpClient.get(url) {
                parameter("u", appSettings.username)
                parameter("p", appSettings.password)
                parameter("v", "1.16.1")
                parameter("c", "myapp")
                parameter("f", "json")
                parameter("id", id)
            }
            response.body<ByteArray>()
        } catch (e: Exception) {
            null
        }
    }


}
