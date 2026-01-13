package io.github.amanbutnot.moosic.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlbumListResponse(
    @SerialName("subsonic-response")
    val subsonicResponse: AlbumListSubsonicResponse
)

@Serializable
data class AlbumListSubsonicResponse(
    val status: String,
    val version: String,
    val type: String,
    val serverVersion: String,
    val openSubsonic: Boolean,
    val albumList: AlbumList
)

@Serializable
data class AlbumList(
    val album: List<AlbumModel> = emptyList()
)

@Serializable
data class AlbumModel(
    val id: String,
    val parent: String? = null,
    val isDir: Boolean? = null,
    val title: String? = null,
    val name: String? = null,
    val album: String? = null,
    val artist: String? = null,
    val year: Int? = null,
    val genre: String? = null,
    val coverArt: String? = null,
    val duration: Int? = null,
    val created: String? = null,
    val artistId: String? = null,
    val songCount: Int? = null,
    val isVideo: Boolean? = null,
    val bpm: Int? = null,
    val comment: String? = null,
    val sortName: String? = null,
    val mediaType: String? = null,
    val musicBrainzId: String? = null,
    val isrc: List<String> = emptyList(),
    val genres: List<GenreModel> = emptyList(),
    val replayGain: ReplayGain? = null,
    val channelCount: Int? = null,
    val samplingRate: Int? = null,
    val bitDepth: Int? = null,
    val moods: List<String> = emptyList(),
    val artists: List<ArtistModel> = emptyList(),
    val displayArtist: String? = null,
    val albumArtists: List<ArtistModel> = emptyList(),
    val displayAlbumArtist: String? = null,
    val contributors: List<Contributor> = emptyList(),
    val displayComposer: String? = null,
    val explicitStatus: String? = null
)

