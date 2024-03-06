package com.bottmac.bottmac.screens

import android.util.Base64
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest


@Composable
fun ProductCard(productName: String, productsFeatures: List<String>, productsImages: List<String>) {
    ElevatedCard(
        modifier = Modifier,
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 8.dp,
            pressedElevation = 4.dp
        )
    ) {
        Column {
            LazyRow {
                items(productsImages) { image ->
                    AsyncImage(
                        modifier = Modifier.size(240.dp),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(
                                Base64.decode(
                                    image,
                                    Base64.DEFAULT
                                )
                            )
                            .crossfade(500)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    )
                }
            }
            Text(text = productName)
            Text(text = "TECHNICAL SPECIFICATION")
            for (feature in productsFeatures) {
                Text(text = " • $feature")
            }
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                item {
                    ElevatedButton(onClick = { /*TODO*/ }) {
                        Text(text = " 📞 Call Now")
                    }
                }
                item {
                    ElevatedButton(onClick = { /*TODO*/ }) {
                        Text(text = " Order Now")
                    }
                }
            }
        }
    }
}
