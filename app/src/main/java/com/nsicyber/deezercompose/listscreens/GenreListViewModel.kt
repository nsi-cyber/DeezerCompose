package com.nsicyber.deezercompose.listscreens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nsicyber.deezercompose.DeezerApiRepository
import com.nsicyber.deezercompose.data.remote.models.GenreModel
import com.nsicyber.deezercompose.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreListViewModel @Inject constructor(
    private val repository: DeezerApiRepository
) : ViewModel() {
    var genreList = mutableStateOf<List<GenreModel>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    init {
        getGenreList()
    }

    fun getGenreList() {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getGenreList()
            val listData = result.data?.data

            when (result) {
                is Resource.Success -> {
                    genreList.value = listData!!
                }

                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false

                }

                is Resource.Loading -> {
                    isLoading.value = true

                }
            }

        }
    }

}