package io.github.amanbutnot.moosic.network

import io.github.amanbutnot.moosic.common.httpClient
import io.github.amanbutnot.moosic.data.model.PingResponse
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class PingRepository {

    suspend fun ping(
        baseUrl: String,
        username: String,
        password: String,
        format: String = "json",
        version: String = "1.16.1",
        client: String = "myapp"
    ): PingResponse? {
        val url = if (baseUrl.endsWith("/")) "${baseUrl}rest/ping.view" else "${baseUrl}/rest/ping.view"
        return try {
            val response = httpClient.get(url) {
                parameter("u", username)
                parameter("p", password)
                parameter("f", format)
                parameter("v", version)
                parameter("c", client)
            }
            response.body<PingResponse>()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
