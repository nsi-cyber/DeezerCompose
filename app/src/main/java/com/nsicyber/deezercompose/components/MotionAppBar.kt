package com.nsicyber.deezercompose.components

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.MarqueeSpacing

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.nsicyber.deezercompose.R
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.layoutId
import coil.compose.AsyncImage
import coil.request.ImageRequest

enum class SwipingStates {
    //our own enum class for stoppages e.g. expanded and collapsed
    EXPANDED, COLLAPSED
}


@Preview
@OptIn(ExperimentalMaterialApi::class, ExperimentalMotionApi::class,
    ExperimentalFoundationApi::class
)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "Range")
@Composable
fun CollapsingBar(
    title: String = "Title",
    imgUrl: String = "https://cdn.webrazzi.com/uploads/2022/04/deezer-237.png",
    content: @Composable() () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
    ) {
        val swipingState = rememberSwipeableState(initialValue = SwipingStates.EXPANDED)
        BoxWithConstraints(//to get the max height
            modifier = Modifier.fillMaxSize()
        ) {
            val heightInPx = with(LocalDensity.current) { maxHeight.toPx() }
            val nestedScrollConnection = remember {
                object : NestedScrollConnection {
                    override fun onPreScroll(
                        available: Offset, source: NestedScrollSource
                    ): Offset {
                        val delta = available.y
                        return if (delta < 0) {
                            swipingState.performDrag(delta).toOffset()
                        } else {
                            Offset.Zero
                        }
                    }

                    override fun onPostScroll(
                        consumed: Offset, available: Offset, source: NestedScrollSource
                    ): Offset {
                        val delta = available.y
                        return swipingState.performDrag(delta).toOffset()
                    }

                    override suspend fun onPostFling(
                        consumed: Velocity, available: Velocity
                    ): Velocity {
                        swipingState.performFling(velocity = available.y)
                        return super.onPostFling(consumed, available)
                    }

                    private fun Float.toOffset() = Offset(0f, this)
                }
            }

            Box(//root container
                modifier = Modifier
                    .fillMaxSize()
                    .swipeable(
                        state = swipingState, thresholds = { _, _ ->
                            FractionalThreshold(0.05f)//it can be 0.5 in general
                        }, orientation = Orientation.Vertical, anchors = mapOf(
                            0f to SwipingStates.COLLAPSED,//min height is collapsed
                            heightInPx to SwipingStates.EXPANDED,//max height is expanded
                        )
                    )
                    .nestedScroll(nestedScrollConnection)
            ) {
                val computedProgress by remember {//progress value will be decided as par state
                    derivedStateOf {
                        if (swipingState.progress.to == SwipingStates.COLLAPSED) swipingState.progress.fraction
                        else 1f - swipingState.progress.fraction
                    }
                }
                val startHeightNum = 300
                MotionLayout(
                    modifier = Modifier.fillMaxSize(),
                    start = ConstraintSet {
                        val header = createRefFor("header")
                        val body = createRefFor("body")
                        val title = createRefFor("title")
                        constrain(header) {
                            this.width = Dimension.matchParent
                            this.height = Dimension.value(300.dp)
                        }
                        constrain(body) {
                            this.width = Dimension.matchParent
                            this.height = Dimension.fillToConstraints
                            this.top.linkTo(header.bottom, 0.dp)
                            this.bottom.linkTo(parent.bottom, 0.dp)
                        }

                        constrain(title) {
                            this.start.linkTo(header.start, 16.dp)
                            this.bottom.linkTo(header.bottom, 24.dp)
                        }
                    },
                    end = ConstraintSet {
                        val header = createRefFor("header")
                        val body = createRefFor("body")
                        val title = createRefFor("title")

                        constrain(header) {
                            this.height = Dimension.value(60.dp)
                            this.alpha = 0f
                        }
                        constrain(body) {
                            this.width = Dimension.matchParent
                            this.height = Dimension.fillToConstraints
                            this.top.linkTo(header.bottom, 0.dp)
                            this.bottom.linkTo(parent.bottom, 0.dp)
                        }

                        constrain(title) {
                            this.start.linkTo(header.start, 16.dp)
                            this.bottom.linkTo(header.bottom)
                            this.top.linkTo(header.top)
                        }
                    },
                    progress = computedProgress,
                ) {

                    Box(
                        modifier = Modifier
                            .layoutId("body")
                            .fillMaxWidth()
                            .background(Color.DarkGray)
                    ) {
                        content()
                    }

                    Box(
                        modifier = Modifier
                            .layoutId("header")
                            .fillMaxWidth()
                            .height(startHeightNum.dp)
                            .background(Color.Gray)
                    ) {


                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(imgUrl)
                                .crossfade(true)
                                .build(),
                            placeholder = painterResource(R.drawable.deezer_logo),
                            contentDescription = null,
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
                    }


                    Text(
                        title,
                        color = Color.White,
                        modifier = Modifier.layoutId("title").basicMarquee(spacing = MarqueeSpacing(16.dp)),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}




