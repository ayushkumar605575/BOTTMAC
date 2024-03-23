package com.bottmac.bottmac.presentation.product_details

import android.util.Base64
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
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
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        ElevatedCard {
            LazyRow {
                items(productsImages) { productsImage ->
                    AsyncImage(
                        modifier = Modifier
                            .height(348.dp)
                            .fillMaxWidth(),
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceEvenly

        ) {

            ElevatedButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Call, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text("Call Now")
            }
            ElevatedButton(onClick = { /*TODO*/ }) {
                Text("Order Now")
            }
        }
    }
}
