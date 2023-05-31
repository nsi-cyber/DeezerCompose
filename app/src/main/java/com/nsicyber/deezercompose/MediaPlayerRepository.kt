package com.nsicyber.deezercompose

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import android.view.View
import androidx.compose.runtime.mutableStateOf
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MediaPlayerRepository @Inject constructor() : MediaPlayerInterface{

    var mediaPlayer = MediaPlayer()

    private var isBuffering = false


    override fun play() {
        onInfo()
        mediaPlayer.prepare()
        AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()
        mediaPlayer.start()
    }

    override fun resume(){
        mediaPlayer.start()
    }

    override fun onInfo(){
        mediaPlayer.setOnBufferingUpdateListener{ mediaPlayer,state ->
            Log.v("MEDIASTATE",state.toString())
            when (state) {
                MediaPlayer.MEDIA_INFO_BUFFERING_START -> isBuffering = true
                MediaPlayer.MEDIA_INFO_BUFFERING_END -> isBuffering = false
            }
        }
    }

    override fun stop() {
        mediaPlayer.reset()
        mediaPlayer.stop()
    }

    override fun pause() {
        mediaPlayer.pause()
    }

    override fun dispose() {
        mediaPlayer.reset()
        mediaPlayer.release()
        mediaPlayer = MediaPlayer()
    }

    override fun setUrl(uri: String?) {
        if(uri == null) return;
        mediaPlayer.setDataSource(uri);
    }

}