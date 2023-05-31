package com.nsicyber.deezercompose

interface MediaPlayerInterface {

    fun play()

    fun stop()

    fun pause()

    fun dispose()

    fun resume()

    fun onInfo()

    fun setUrl(uri : String?)

}