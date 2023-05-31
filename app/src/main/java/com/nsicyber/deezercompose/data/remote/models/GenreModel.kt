package com.nsicyber.deezercompose.data.remote.models


import com.google.gson.annotations.SerializedName
import java.io.Serializable

class GenreModel : Serializable {
    @SerializedName("id")
    var id: String? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("picture_xl")
    var pictureXl: String? = null

    @SerializedName("type")
    var type: String? = null

}

class ArrayGenreModel : Serializable {
    @SerializedName("data")
    var data: List<GenreModel> = listOf()
}