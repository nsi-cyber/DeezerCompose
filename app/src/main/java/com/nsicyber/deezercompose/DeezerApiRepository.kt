package com.nsicyber.deezercompose

import com.nsicyber.deezercompose.data.remote.models.ArrayAlbumModel
import com.nsicyber.deezercompose.data.remote.models.ArrayArtistModel
import com.nsicyber.deezercompose.data.remote.models.ArrayGenreModel
import com.nsicyber.deezercompose.data.remote.models.ArrayMusicModel
import com.nsicyber.deezercompose.data.remote.retrofit.DeezerApi
import com.nsicyber.deezercompose.utils.Resource
import dagger.hilt.android.scopes.ActivityScoped
import java.lang.Exception
import javax.inject.Inject

@ActivityScoped
class DeezerApiRepository @Inject constructor(
    private val api:DeezerApi
) {
    suspend fun getGenreList():Resource<ArrayGenreModel>{
        val response=try {
           api.getGenreList()
        }
        catch (e:Exception){
            return Resource.Error("error")
        }
        return Resource.Success(response)
    }


    suspend fun getArtistList(genreId:Int):Resource<ArrayArtistModel>{
        val response=try {
            api.getArtistList(genreId)
        }
        catch (e:Exception){
            return Resource.Error("error")
        }
        return Resource.Success(response)    }



    suspend fun getAlbumList(artistId:Int):Resource<ArrayAlbumModel>{
        val response=try {
            api.getAlbumList(artistId)
        }
        catch (e:Exception){
            return Resource.Error("error")
        }
        return Resource.Success(response)    }



    suspend fun getMusicList(albumId:Int):Resource<ArrayMusicModel>{
        val response=try {
            api.getMusicList(albumId).tracks!!
        }
        catch (e:Exception){
            return Resource.Error("error")
        }
        return Resource.Success(response)    }
}