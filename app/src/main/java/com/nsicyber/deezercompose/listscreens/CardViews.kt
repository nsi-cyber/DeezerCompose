package com.nsicyber.deezercompose.listscreens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.MarqueeSpacing
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nsicyber.deezercompose.R
import com.nsicyber.deezercompose.data.remote.models.AlbumModel
import com.nsicyber.deezercompose.data.remote.models.ListCard
import com.nsicyber.deezercompose.data.remote.models.MusicModel
import com.nsicyber.deezercompose.utils.convertDate

@Composable
fun MiniCard(
    model: ListCard,
    modifier: Modifier = Modifier,
    completion: (model: ListCard) -> Unit
) {

    Box(contentAlignment = Alignment.Center,
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .clickable {
                completion(model)
            }


    ) {


        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(model.imgUrl)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.deezer_logo),
            contentDescription = model.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .drawWithCache {
                    val gradient = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        startY = size.height / 3,
                        endY = size.height
                    )
                    onDrawWithContent {
                        drawContent()
                        drawRect(gradient, blendMode = BlendMode.Multiply)
                    }
                })

        Text(
            text = model.title!!,
            color = Color.LightGray,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp)
        )


    }

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SongCard(
    model: MusicModel,
    modifier: Modifier = Modifier,
    completion: (model: MusicModel) -> Unit
) {

    Box(contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                completion(model)
            }


    ) {


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 8.dp, bottom = 8.dp, start = 4.dp)
    ) {

        Text(
            text = model.title!!,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(bottom = 2.dp),  maxLines = 1,
            overflow = TextOverflow.Ellipsis

        )

        Text(
            text = model.artist!!.name!!,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,

        )


    }}

}




@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AlbumCard(
    model: AlbumModel,
    modifier: Modifier = Modifier,
    completion: (model: AlbumModel) -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .clickable {
                completion(model)
            }
    ) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(model.coverXl)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.deezer_logo),
            contentDescription = model.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .shadow(5.dp, RoundedCornerShape(10.dp))
                .fillMaxSize()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(10.dp))

        )


        Text(
            text = model.title!!,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 4.dp, bottom = 2.dp),
            maxLines = 1,
           overflow = TextOverflow.Ellipsis

        )

        Text(
            text = convertDate(model.releaseDate!!),
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )


    }

}

@Composable
fun CardRows(
    rowIndex: Int, cards: List<*>, modifier: Modifier,
    completion: (model: ListCard) -> Unit
) {

    var newCards = Gson().fromJson<List<Map<*, *>>>(
        Gson().toJson(cards),
        object : TypeToken<List<Map<*, *>>>() {}.type
    )


    Column {
        Row {
            MiniCard(
                model = ListCard(
                    newCards[rowIndex * 2].get("id").toString().toDouble().toInt(),
                    (newCards[rowIndex * 2].get("picture_xl")
                        ?: newCards[rowIndex * 2].get("cover_xl")).toString(),
                    (newCards[rowIndex * 2].get("title")
                        ?: newCards[rowIndex * 2].get("name")).toString()
                ),
                modifier = modifier.weight(1f)
            ) {
                completion(it)
            }
            Spacer(modifier = Modifier.width(16.dp))
            if (newCards.size >= rowIndex * 2 + 2) {
                MiniCard(
                    model = ListCard(
                        newCards[rowIndex * 2 + 1].get("id").toString().toDouble().toInt(),
                        (newCards[rowIndex * 2 + 1].get("picture_xl")
                            ?: newCards[rowIndex * 2 + 1].get("cover_xl")).toString(),
                        (newCards[rowIndex * 2 + 1].get("title")
                            ?: newCards[rowIndex * 2 + 1].get("name")).toString()
                    ),
                    modifier = modifier.weight(1f)
                ) {
                    completion(it)
                }


            } else
                Spacer(modifier = Modifier.weight(1f))


        }
        Spacer(modifier = Modifier.height(16.dp))


    }
}

@Composable
fun AlbumCardRows(
    rowIndex: Int, cards: List<AlbumModel>, modifier: Modifier,
    completion: (model: AlbumModel) -> Unit
) {


    Column {
        Row {
            AlbumCard(
                model =
                cards[rowIndex * 2],
                modifier = modifier.weight(1f)
            ) {
                completion(it)
            }
            Spacer(modifier = Modifier.width(16.dp))
            if (cards.size >= rowIndex * 2 + 2) {
                AlbumCard(
                    model =
                    cards[rowIndex * 2 + 1],
                    modifier = modifier.weight(1f)
                ) {
                    completion(it)
                }


            } else
                Spacer(modifier = Modifier.weight(1f))


        }
        Spacer(modifier = Modifier.height(16.dp))


    }
}

@Composable
fun ArtistRowList(
    genreId: Int,
    navController: NavController,
    modifier: Modifier,
) {
    var viewModel = hiltViewModel<ArtistListViewModel>()

    LaunchedEffect(Unit) {
        viewModel.getArtistList(genreId)
    }

    val artistList by remember {
        viewModel.artistList
    }

    val loadError by remember {
        viewModel.loadError
    }

    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        val itemCount = if (artistList.size % 2 == 0) {
            artistList.size / 2
        } else {
            artistList.size / 2 + 1
        }
        items(itemCount) {
            CardRows(
                rowIndex = it,
                cards = artistList,
                modifier = modifier
            ) {
                navController.navigate("album_list_screen?artistId=${it.id}&title=${it.title}&coverUrl=${it.imgUrl}")
            }
        }
    }

}

@Composable
fun GenreRowList(
    navController: NavController,
    modifier: Modifier,
) {
    var viewModel = hiltViewModel<GenreListViewModel>()
    val genreList by remember {
        viewModel.genreList
    }

    val loadError by remember {
        viewModel.loadError
    }

    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        val itemCount = if (genreList.size % 2 == 0) {
            genreList.size / 2
        } else {
            genreList.size / 2 + 1
        }
        items(itemCount) {
            CardRows(
                rowIndex = it,
                cards = genreList,
                modifier = modifier
            ) {
                navController.navigate("artist_list_screen?genreId=${it.id}&title=${it.title}&coverUrl=${it.imgUrl}")
            }
        }
    }

}

@Composable
fun AlbumRowList(
    artistId: Int,
    navController: NavController,
    modifier: Modifier,
) {
    var viewModel = hiltViewModel<AlbumListViewModel>()

    LaunchedEffect(Unit) {
        viewModel.getAlbumList(artistId)
    }

    val albumList by remember {
        viewModel.albumList
    }

    val loadError by remember {
        viewModel.loadError
    }

    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        val itemCount = if (albumList.size % 2 == 0) {
            albumList.size / 2
        } else {
            albumList.size / 2 + 1
        }
        items(itemCount) {
            AlbumCardRows(
                rowIndex = it,
                cards = albumList.sortedByDescending { it.releaseDate },
                modifier = modifier
            ) {
                navController.navigate("music_list_screen?albumId=${it.id}&title=${it.title}&coverUrl=${it.coverXl}")
            }
        }
    }

}


@Composable
fun MusicRowList(
    albumId: Int,
    navController: NavController,
    modifier: Modifier,
    completion: (model: MusicModel) -> Unit

) {
    var viewModel = hiltViewModel<MusicListViewModel>()

    LaunchedEffect(Unit) {
        viewModel.getMusicList(albumId)
    }

    val musicList by remember {
        viewModel.musicList
    }

    val loadError by remember {
        viewModel.loadError
    }

    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        items(musicList.size) {
            SongCard(
                model =
                musicList[it],
            ) {
completion(it)
            }
            if(it+1<musicList.size){
                Column(Modifier.height(1.dp).fillMaxWidth(1f).background(Color.Black)){}
            }

        }
    }

}