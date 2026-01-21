package io.github.amanbutnot.moosic.data.constants

import io.github.amanbutnot.moosic.common.appSettings

fun getImage(id: String): String {
    val baseUrl = appSettings.serverUrl
    return if (baseUrl.endsWith("/")) "${baseUrl}rest/getCoverArt.view?u=${appSettings.username}&p=${appSettings.password}&v=1.16.1&c=feagegsag&f=json&id=$id" else "${baseUrl}/rest/getCoverArt.view?u=${appSettings.username}&p=${appSettings.password}&v=1.16.1&c=feagegsag&f=json&id=$id&size=480"
}

fun getStreamUrl(id: String): String {
    val baseUrl = appSettings.serverUrl
    val separator = if (baseUrl.endsWith("/")) "" else "/"
    return "${baseUrl}${separator}rest/stream.view?u=${appSettings.username}&p=${appSettings.password}&v=1.16.1&c=feagegsag&f=json&id=$id"
}
