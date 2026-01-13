package io.github.amanbutnot.moosic.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoModel(
    @SerialName("`subsonic-response`")
    val subsonicResponse1: SubsonicResponse1
)

@Serializable
data class SubsonicResponse1(
    val status: String,
    val version: String,
    val type: String,
    val serverVersion: String,
    val openSubsonic: Boolean,
    val user: User
)

@Serializable
data class User(
    val username: String,
    val scrobblingEnabled: Boolean,
    val adminRole: Boolean,
    val settingsRole: Boolean,
    val downloadRole: Boolean,
    val uploadRole: Boolean,
    val playlistRole: Boolean,
    val coverArtRole: Boolean,
    val commentRole: Boolean,
    val podcastRole: Boolean,
    val streamRole: Boolean,
    val jukeboxRole: Boolean,
    val shareRole: Boolean,
    val videoConversionRole: Boolean
)
