package com.nsicyber.deezercompose.listscreens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.nsicyber.deezercompose.components.CollapsingBar

@Composable
fun AlbumListScreen(
    navController: NavController,
    artistId: Int,
    title: String,
    coverUrl: String
) {

    CollapsingBar(title, coverUrl) {
        AlbumRowList(navController = navController, modifier = Modifier, artistId = artistId)
    }

}
