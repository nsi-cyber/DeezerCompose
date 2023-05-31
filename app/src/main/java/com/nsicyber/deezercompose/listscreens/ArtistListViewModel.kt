package com.nsicyber.deezercompose.listscreens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nsicyber.deezercompose.DeezerApiRepository
import com.nsicyber.deezercompose.data.remote.models.ArrayAlbumModel
import com.nsicyber.deezercompose.data.remote.models.ArrayArtistModel
import com.nsicyber.deezercompose.data.remote.models.ArtistModel
import com.nsicyber.deezercompose.data.remote.models.GenreModel
import com.nsicyber.deezercompose.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ArtistListViewModel @Inject constructor(
    private val repository: DeezerApiRepository
) : ViewModel() {
    var artistList = mutableStateOf<List<ArtistModel>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    suspend fun getArtistList(genreId:Int){
        artistList.value=repository.getArtistList(genreId).data!!.data
    }

}