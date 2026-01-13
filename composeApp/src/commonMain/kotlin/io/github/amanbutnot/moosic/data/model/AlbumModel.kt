package io.github.amanbutnot.moosic.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlbumResponse(
    @SerialName("subsonic-response")
    val subsonicResponse: AlbumSubsonicResponse
)

@Serializable
data class AlbumSubsonicResponse(
    val status: String,
    val version: String,
    val type: String,
    val serverVersion: String,
    val openSubsonic: Boolean,
    val album: Album
)

@Serializable
data class Album(
    val id: String,
    val name: String,
    val artist: String,
    val artistId: String,
    val coverArt: String,
    val songCount: Int,
    val duration: Int,
    val created: String,
    val year: Int,
    val genre: String,
    val userRating: Int,
    val genres: List<Genre>,
    val musicBrainzId: String,
    val isCompilation: Boolean,
    val sortName: String,
    val discTitles: List<String>,
    val originalReleaseDate: OriginalReleaseDate,
    val releaseDate: ReleaseDate,
    val releaseTypes: List<String>,
    val recordLabels: List<RecordLabel>,
    val moods: List<String>,
    val artists: List<ArtistModel>,
    val displayArtist: String,
    val explicitStatus: String,
    val version: String,
    val song: Song
)

@Serializable
data class Song(
    val id: String,
    val parent: String,
    val isDir: Boolean,
    val title: String,
    val album: String,
    val artist: String,
    val track: Int,
    val year: Int,
    val genre: String,
    val coverArt: String,
    val size: Int,
    val contentType: String,
    val suffix: String,
    val duration: Int,
    val bitRate: Int,
    val path: String,
    val created: String,
    val albumId: String,
    val artistId: String,
    val type: String,
    val isVideo: Boolean,
    val bpm: Int,
    val comment: String,
    val sortName: String,
    val mediaType: String,
    val musicBrainzId: String?,
    val isrc: List<String>,
    val genres: List<Genre>,
    val replayGain: ReplayGain,
    val channelCount: Int,
    val samplingRate: Int,
    val bitDepth: Int,
    val moods: List<String>,
    val artists: List<ArtistModel>,
    val displayArtist: String,
    val albumArtists: List<ArtistModel>,
    val displayAlbumArtist: String,
    val contributors: List<Contributor>,
    val displayComposer: String,
    val explicitStatus: String
)


@Serializable
data class RecordLabel(
    val name: String
)

@Serializable
data class Genre(
    val name: String
)

@Serializable
data class OriginalReleaseDate(
    val changeMe: String
)

@Serializable
data class ReleaseDate(
    val changeMe: String
)



@Serializable
data class GenreModel(
    val name: String
)

@Serializable
data class ArtistModel(
    val id: String,
    val name: String
)

@Serializable
data class ReplayGain(
    val trackGain: Double? = null,
    val trackPeak: Double? = null,
    val albumGain: Double? = null,
    val albumPeak: Double? = null
)

@Serializable
data class Contributor(
    val role: String? = null,
    val artist: ArtistModel
)
