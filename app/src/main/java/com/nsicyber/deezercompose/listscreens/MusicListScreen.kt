package com.nsicyber.deezercompose.listscreens

import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nsicyber.deezercompose.components.CollapsingBar
import com.nsicyber.deezercompose.components.PlaySongDialog
import com.nsicyber.deezercompose.data.remote.models.MusicModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun MusicListScreen(
    navController: NavController,
    albumId: Int,
    title: String,
    coverUrl: String
) {
    //Lets define bottomSheetScaffoldState which will hold the state of Scaffold
    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )

    var viewModel = hiltViewModel<MediaPlayerViewModel>()

    var musicModel by remember {
        mutableStateOf<MusicModel?>(null)
    }


    if (!modalBottomSheetState.isVisible) {
        viewModel.stopMediaPlayer()
    }



    var scope = rememberCoroutineScope();
    ModalBottomSheetLayout(

        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topEnd = 30.dp),
        sheetContent = { PlaySongDialog(musicModel) },

        ) {


        CollapsingBar(title, coverUrl) {
            MusicRowList(navController = navController, modifier = Modifier, albumId = albumId) {
                scope.launch {
                    musicModel = it
                    modalBottomSheetState.show()
                    viewModel.startMediaPlayer(it.preview)
                }
            }
        }

    }
}
