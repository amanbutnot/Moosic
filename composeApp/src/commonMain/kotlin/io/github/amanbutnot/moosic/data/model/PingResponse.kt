package io.github.amanbutnot.moosic.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PingResponse(
    @SerialName("subsonic-response")
    val subsonicResponse: SubsonicResponse
)

@Serializable
data class SubsonicResponse(
    val status: String,
    val version: String,
    val type: String,
    val serverVersion: String,
    val openSubsonic: Boolean
)
