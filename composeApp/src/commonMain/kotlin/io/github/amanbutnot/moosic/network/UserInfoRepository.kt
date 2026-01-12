package io.github.amanbutnot.moosic.network

import io.github.amanbutnot.moosic.common.appSettings
import io.github.amanbutnot.moosic.common.httpClient
import io.github.amanbutnot.moosic.data.model.UserInfoModel
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class UserInfoRepository {
    suspend fun getUserInfo(
    ): UserInfoModel? {
        val baseUrl = appSettings.serverUrl
        val url = if (baseUrl.endsWith("/")) "${baseUrl}rest/getUser.view" else "${baseUrl}/rest/getUser.view"
        
        return try {
            val response = httpClient.get(url) {
                parameter("u", appSettings.username)
                parameter("p", appSettings.password)
                parameter("username", appSettings.username)
                parameter("f", "json")
                parameter("v", "1.16.1")
                parameter("c", "myapp")
            }
            response.body<UserInfoModel>()
        } catch (e: Exception) {
            null
        }
    }
}
