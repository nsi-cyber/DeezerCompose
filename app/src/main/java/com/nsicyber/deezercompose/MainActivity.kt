package com.nsicyber.deezercompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nsicyber.deezercompose.listscreens.AlbumListScreen
import com.nsicyber.deezercompose.listscreens.ArtistListScreen
import com.nsicyber.deezercompose.listscreens.GenreListScreen
import com.nsicyber.deezercompose.listscreens.MusicListScreen
import com.nsicyber.deezercompose.ui.theme.DeezerComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.concurrent.timerTask

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DeezerComposeTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "genres_list_screen") {
                    
                    composable("genres_list_screen") {
                        GenreListScreen(navController = navController)
                    }

                    composable(
                        "artist_list_screen?genreId={genreId}&title={title}&coverUrl={coverUrl}",
                        arguments = listOf(
                            navArgument("genreId") {
                                type = NavType.IntType
                            } ,navArgument("title") {
                                type = NavType.StringType
                            },navArgument("coverUrl") {
                                type = NavType.StringType
                            }
                        )
                    ) {

                        val genreId = remember {
                            it.arguments?.getInt("genreId")
                        }
                        val title = remember {
                            it.arguments?.getString("title")
                        }
                        val coverUrl = remember {
                            it.arguments?.getString("coverUrl")
                        }
                        ArtistListScreen(navController = navController,genreId!!,title!!,coverUrl!!)
                    }

                    composable(
                        "album_list_screen?artistId={artistId}&title={title}&coverUrl={coverUrl}",
                        arguments = listOf(
                            navArgument("artistId") {
                                type = NavType.IntType
                            } ,navArgument("title") {
                                type = NavType.StringType
                            },navArgument("coverUrl") {
                                type = NavType.StringType
                            }
                        )
                    ) {

                        val artistId = remember {
                            it.arguments?.getInt("artistId")
                        }
                        val title = remember {
                            it.arguments?.getString("title")
                        }
                        val coverUrl = remember {
                            it.arguments?.getString("coverUrl")
                        }
                        AlbumListScreen(navController = navController,artistId!!,title!!,coverUrl!!)

                    }



                    composable(
                        "music_list_screen?albumId={albumId}&title={title}&coverUrl={coverUrl}",
                        arguments = listOf(
                            navArgument("albumId") {
                                type = NavType.IntType
                            } ,navArgument("title") {
                                type = NavType.StringType
                            },navArgument("coverUrl") {
                                type = NavType.StringType
                            }
                        )
                    ) {

                        val title = remember {
                            it.arguments?.getString("title")
                        }
                        val coverUrl = remember {
                            it.arguments?.getString("coverUrl")
                        }
                        val albumId = remember {
                            it.arguments?.getInt("albumId")
                        }
                        MusicListScreen(navController=navController,albumId=albumId!!, title = title!!, coverUrl = coverUrl!!)
                    }


                }


            }
        }
    }
}