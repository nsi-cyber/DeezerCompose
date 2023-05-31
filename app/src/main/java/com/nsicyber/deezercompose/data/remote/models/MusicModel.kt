package com.nsicyber.deezercompose.data.remote.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class MusicModel : Serializable {
    @SerializedName("id")
    var id: String? = null

    @SerializedName("title")
    var title: String? = null

    @SerializedName("duration")
    var duration: String? = null

    @SerializedName("preview")
    var preview: String? = null

    @SerializedName("artist")
    var artist: ArtistModel? = ArtistModel()

    @SerializedName("album")
    var album: AlbumModel? = AlbumModel()


}

class ArrayMusicModel : java.io.Serializable {
    @SerializedName("data")
    var data: ArrayList<MusicModel> = arrayListOf()
}
