package com.nsicyber.deezercompose.data.remote.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class AlbumModel : Serializable {
    @SerializedName("id")
    var id: String? = null

    @SerializedName("title")
    var title: String? = null

    @SerializedName("cover_xl")
    var coverXl: String? = null

    @SerializedName("genre_id")
    var genreId: Int? = null

    @SerializedName("genres")
    var genres: ArrayGenreModel? = null

    @SerializedName("duration")
    var duration: Int? = null

    @SerializedName("release_date")
    var releaseDate: String? = null

    @SerializedName("contributors")
    var contributors: ArrayList<ArtistModel> = arrayListOf()

    @SerializedName("artist")
    var artist: ArtistModel? = null

    @SerializedName("type")
    var type: String? = null

    @SerializedName("tracks")
    var tracks: ArrayMusicModel? = null

}

class ArrayAlbumModel : Serializable {
    @SerializedName("data")
    var data: ArrayList<AlbumModel> = arrayListOf()
}