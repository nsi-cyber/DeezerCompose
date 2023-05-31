package com.nsicyber.deezercompose.components

import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.layoutId
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nsicyber.deezercompose.R
import com.nsicyber.deezercompose.data.remote.models.MusicModel
import com.nsicyber.deezercompose.listscreens.MediaPlayerViewModel
import kotlinx.coroutines.launch

@Composable
fun PlaySongDialog(musicModel: MusicModel?) {
    musicModel?.let {

        var viewModel = hiltViewModel<MediaPlayerViewModel>()

    val rotation = remember { Animatable(0f) }
    var isRotating by remember { mutableStateOf(true) }

        var scope = rememberCoroutineScope();



        LaunchedEffect(isRotating) {
            if (isRotating) {
                rotation.animateTo(
                    360f, animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 5000, easing = LinearEasing),
                        repeatMode = RepeatMode.Restart
                    )
                )
            } else {
                rotation.stop()
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            backgroundColor = Color.Gray,
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .padding(top = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(

                        model = ImageRequest.Builder(LocalContext.current)
                            .data(musicModel.album!!.coverXl)
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(R.drawable.deezer_logo),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(180.dp)
                            .layoutId("image").aspectRatio(1f)
                            .rotate(rotation.value).clip(shape = RoundedCornerShape(99.dp)),

                        )
                    Image(
                        painter = if (isRotating == true) painterResource(R.drawable.ic_pause)
                        else
                            painterResource(R.drawable.ic_play),
                        contentDescription = null,
                        modifier = Modifier
                            .layoutId("button")
                            .size(40.dp)
                            .align(Alignment.Center)
                            .clickable {
                                if (isRotating == true)
                                    viewModel.pauseMediaPlayer()
                                else
                                    viewModel.resumeMediaPlayer()
                                isRotating = !isRotating
                            }

                    )
                }
                Text(
                    text = musicModel.title!!,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(vertical = 14.dp)
                )
                Text(
                    text = musicModel.album?.title!!,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = musicModel.artist?.name!!,
                    modifier = Modifier.padding(bottom = 40.dp)
                )
            }


        }
    }
}