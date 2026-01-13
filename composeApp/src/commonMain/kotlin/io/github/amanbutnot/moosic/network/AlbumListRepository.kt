package io.github.amanbutnot.moosic.network

import io.github.amanbutnot.moosic.common.appSettings
import io.github.amanbutnot.moosic.common.httpClient
import io.github.amanbutnot.moosic.data.model.AlbumListResponse
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText


suspend fun getAlbumList(
    sort: String = "newest",
    size: Int = 10,
    offset: Int = 0
): AlbumListResponse? {
    val baseUrl = appSettings.serverUrl
    val url =
        if (baseUrl.endsWith("/")) "${baseUrl}rest/getAlbumList.view" else "${baseUrl}/rest/getAlbumList.view"

    return try {
        val response = httpClient.get(url) {
            parameter("u", appSettings.username)
            parameter("p", appSettings.password)
            parameter("v", "1.16.1")
            parameter("c", "myapp")
            parameter("type", sort)
            parameter("size", size)
            parameter("f", "json")
            parameter("offset", offset)
        }
        println(response.bodyAsText())
        response.body<AlbumListResponse>()
    } catch (e: Exception) {
        null
    }
}

