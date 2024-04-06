package com.bottmac.bottmac.presentation.product_details

import android.util.Base64
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun ProductDetailsScreen(
    modifier: Modifier,
    productName: String,
    productsFeatures: List<String>,
    productsImages: List<String>,
    onBack: () -> Unit,
) {
    BackHandler {
        onBack()
    }
//    var activeProduct by remember {
//        mutableStateOf<ByteArray?>(null)
//    }
    var isDialogBox by rememberSaveable {
        mutableStateOf(false)
    }

    if (isDialogBox) {
        ProductDialogBox { isDialogBox = false }
    }

//    if (activeProduct != null) {
//        Column(
//            modifier = modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ){
//            ProductView(
//                modifier = modifier.fillMaxSize(),
//                activeProduct = activeProduct!!,
//            ) { activeProduct = null }
//        }
//    }
//    else {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        ElevatedCard(
            modifier = Modifier
                .height(348.dp)
                .fillMaxWidth(),
        ) {
            LazyRow {
                items(productsImages) { productsImage ->
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxSize(),
//                                .clickable {
//                                    activeProduct = Base64.decode(
//                                        productsImage,
//                                        Base64.DEFAULT
//                                    )
//                                },
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(
                                Base64.decode(
                                    productsImage,
                                    Base64.DEFAULT
                                )
                            )
                            .crossfade(500)
                            .build(),
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )
                }

            }

        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = productName,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 32.sp,
            fontFamily = FontFamily.SansSerif
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Technical Specification",
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            fontStyle = FontStyle.Italic,
            textDecoration = TextDecoration.Underline
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn {
            items(productsFeatures) { productsFeature ->
                Text(
                    text = "• $productsFeature",
                    fontSize = 20.sp,
                )
            }
        }
        ElevatedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp, bottom = 8.dp),
            onClick = { isDialogBox = true },
        ) {
            Icon(imageVector = Icons.Default.Call, contentDescription = null)
            Spacer(modifier = Modifier.width(4.dp))
            Text("Call Now")
        }
    }
//    }
}

//@Composable
//fun ProductView(modifier: Modifier = Modifier, activeProduct: ByteArray, onDismiss: () -> Unit) {
//    Box(
//        modifier = modifier,
//        contentAlignment = Alignment.Center
//    ) {
////        Scrim(modifier = modifier, onClose = onDismiss)
//        DisplayProduct(modifier, activeProduct)
//    }
//}

//@Composable
//fun Scrim(modifier: Modifier, onClose: () -> Unit) {
//    Box(modifier = modifier
//        .pointerInput(Unit) { detectTapGestures { onClose() } }
//        .background(Color.DarkGray.copy(alpha = 0.75f))
//    )
//}

//@Composable
//fun DisplayProduct(modifier: Modifier = Modifier, activeProduct: ByteArray) {
//    var offSet by remember {
//        mutableStateOf(Offset.Zero)
//    }
//    var zoom by remember {
//        mutableFloatStateOf(0f)
//    }
//    AsyncImage(
//        modifier = modifier
//            .aspectRatio(1f)
//            .clipToBounds()
////            .pointerInput(Unit) {
////                detectTransformGestures { centroid, pan, gestureZoom, _ ->
////                    offSet = offSet.calculateNewOffset(centroid, pan, zoom, gestureZoom, size)
////                    zoom = maxOf(1f, zoom * gestureZoom)
////                }
////            }
//            .pointerInput(Unit) {
//                detectTapGestures(
//                    onDoubleTap = {offset ->
//                        zoom = if (zoom > 1f) 1f else 2f
//                        offSet = calculateDoubleTapOffset(zoom, size, offset)
//                        if (zoom == 1f) {
//                            offSet = Offset.Zero
//                        }
//                    }
//                )
//            }
//            .graphicsLayer {
//                translationX = -offSet.x
//                translationY = -offSet.y
//                scaleX = zoom
//                scaleY = zoom
//                transformOrigin = TransformOrigin(0f, 0f)
//            },
//        model = ImageRequest.Builder(LocalContext.current)
//            .data(activeProduct)
//            .crossfade(500)
//            .build(),
//        contentScale = ContentScale.Crop,
//        contentDescription = null
//    )
//}

//private fun Offset.calculateNewOffset(
//    centroid: Offset,
//    pan: Offset,
//    zoom: Float,
//    gestureZoom: Float,
//    size: IntSize
//): Offset {
//    // 1. Calculate the focal point for zooming
//    val focalPoint = centroid + pan
//
//    // 2. Apply zoom factor relative to focal point
//    val newOffsetX = (this.x - focalPoint.x) * zoom * gestureZoom + focalPoint.x
//
//    // 3. Apply zoom factor relative to focal point for y-axis
//    val newOffsetY = (this.y - focalPoint.y) * zoom * gestureZoom + focalPoint.y
//
//    // 4. Clamp the new offset within image bounds (optional)
//    val clampedOffsetX = newOffsetX.coerceIn(0f, size.width.toFloat() - 1f)
//    val clampedOffsetY = newOffsetY.coerceIn(0f, size.height.toFloat() - 1f)
//
//    // 5. Return the new offset
//    return Offset(clampedOffsetX, clampedOffsetY)
//}
//private fun Offset.calculateNewOffset(
//    centroid: Offset,
//    pan: Offset,
//    zoom: Float,
//    gestureZoom: Float,
//    size: IntSize
//): Offset {
//return Offset(centroid.x, centroid.y)
//}
//
//fun calculateDoubleTapOffset(zoom: Float, size: IntSize, offset: Offset): Offset {
//    println(zoom)
//    println(size)
//    println(offset)
////    val newOffSet = Offset(-(offset.x - (size.width/2f)).coerceIn(-size.width/2f, size.width/2f),0f)
//
//
//    // No need to calculate center offset as zooming happens around current position
//    return Offset(
//       x = -(offset.x - (size.width/2f)).coerceIn(-size.width/2f, size.width/2f),
//        y = 0f
//    )
//}


