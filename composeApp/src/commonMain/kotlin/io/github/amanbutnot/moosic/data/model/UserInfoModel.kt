package io.github.amanbutnot.moosic.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoModel(
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