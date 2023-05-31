package com.nsicyber.deezercompose.data.remote.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ArtistModel : Serializable {

    @SerializedName("id")
    var id: String? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("picture_xl")
    var pictureXl: String? = null

    @SerializedName("type")
    var type: String? = null


}


class ArrayArtistModel : Serializable {
    @SerializedName("data")
    var data: ArrayList<ArtistModel> = arrayListOf()
}