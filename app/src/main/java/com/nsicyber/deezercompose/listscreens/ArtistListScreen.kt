package com.nsicyber.deezercompose.listscreens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.nsicyber.deezercompose.components.CollapsingBar

@Composable
fun ArtistListScreen(
    navController: NavController,
    genreId: Int,
    title: String,
    coverUrl: String
) {

    CollapsingBar(title, coverUrl) {
        ArtistRowList(navController = navController, modifier = Modifier, genreId = genreId)
    }

}
