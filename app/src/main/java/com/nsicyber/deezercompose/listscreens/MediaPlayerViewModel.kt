package com.nsicyber.deezercompose.listscreens

import androidx.lifecycle.ViewModel
import com.nsicyber.deezercompose.MediaPlayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MediaPlayerViewModel @Inject constructor(private var mediaPlayerRepository: MediaPlayerRepository) : ViewModel(){

    fun startMediaPlayer(uri : String?){
        try {
            mediaPlayerRepository.setUrl(uri)
            mediaPlayerRepository.play()
        }
        catch (e : Exception){}
    }

    fun resumeMediaPlayer(){
        mediaPlayerRepository.resume()
    }

    fun stopMediaPlayer(){
        mediaPlayerRepository.stop()
        mediaPlayerRepository.dispose()
    }

    fun pauseMediaPlayer() = mediaPlayerRepository.pause()

}