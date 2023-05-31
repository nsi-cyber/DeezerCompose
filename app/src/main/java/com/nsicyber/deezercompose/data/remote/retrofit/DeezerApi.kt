package com.nsicyber.deezercompose.data.remote.retrofit

import com.nsicyber.deezercompose.data.remote.models.AlbumModel
import com.nsicyber.deezercompose.data.remote.models.ArrayAlbumModel
import com.nsicyber.deezercompose.data.remote.models.ArrayArtistModel
import com.nsicyber.deezercompose.data.remote.models.ArrayGenreModel
import com.nsicyber.deezercompose.data.remote.models.ArrayMusicModel
import retrofit2.http.GET
import retrofit2.http.Path

interface DeezerApi {
    @GET("/genre")
    suspend fun getGenreList():ArrayGenreModel

    @GET("/genre/{genreId}/artists")
    suspend fun getArtistList(@Path("genreId") genreId:Int):ArrayArtistModel

    @GET("/artist/{artistId}/albums")
    suspend fun getAlbumList(@Path("artistId") artistId:Int):ArrayAlbumModel

    @GET("/album/{albumId}/")
    suspend fun getMusicList(@Path("albumId") albumId:Int):AlbumModel



}