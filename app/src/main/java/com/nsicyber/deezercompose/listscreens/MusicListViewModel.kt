package com.nsicyber.deezercompose.listscreens


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.nsicyber.deezercompose.DeezerApiRepository
import com.nsicyber.deezercompose.data.remote.models.MusicModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MusicListViewModel @Inject constructor(
    private val repository: DeezerApiRepository
) : ViewModel() {
    var musicList = mutableStateOf<List<MusicModel>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    suspend fun getMusicList(albumId:Int){
        musicList.value=repository.getMusicList(albumId).data!!.data
    }

}