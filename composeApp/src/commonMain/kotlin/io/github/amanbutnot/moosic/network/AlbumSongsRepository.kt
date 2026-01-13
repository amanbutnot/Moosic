package io.github.amanbutnot.moosic.network

import io.github.amanbutnot.moosic.common.appSettings
import io.github.amanbutnot.moosic.common.httpClient
import io.github.amanbutnot.moosic.data.model.AlbumSongsResponse
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText

/**
 * Fetches the list of songs for a specific album from the Subsonic server.
 *
 * @param id The unique identifier of the album.
 * @return An [AlbumSongsResponse] containing the album songs if successful, or null if an error occurs.
 */
suspend fun getAlbumSongs(
    id: String
): AlbumSongsResponse? {
    val baseUrl = appSettings.serverUrl
    val url =
        if (baseUrl.endsWith("/")) "${baseUrl}rest/getAlbum.view" else "${baseUrl}/rest/getAlbum.view"

    return try {
        val response = httpClient.get(url) {
            parameter("u", appSettings.username)
            parameter("p", appSettings.password)
            parameter("v", "1.16.1")
            parameter("c", "myapp")
            parameter("id", id)
            parameter("f", "json")
        }
        println(response.bodyAsText())
        response.body<AlbumSongsResponse>()
    } catch (e: Exception) {
        println(e.message)
        null
    }
}
