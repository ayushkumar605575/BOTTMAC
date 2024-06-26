package com.bottmac.bottmac.presentation.product_details

import android.util.Base64
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bottmac.bottmac.models.ProductItem

//internal val placedItems = mutableListOf<ProductItem>()

@Composable
fun ProductCard(
    product: ProductItem,
    guestUser: Boolean,
    onClick: () -> Unit
) {
    val productsFeatures = product.productFeatures.split("\\n")

    var isDialogBox by rememberSaveable {
        mutableStateOf(false)
    }

    AnimatedVisibility(visible = isDialogBox) {
        ProductDialogBox { isDialogBox = false }
    }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .padding(vertical = 8.dp, horizontal = 8.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Top
        ) {
            AsyncImage(
                modifier = Modifier.size(
                    width = 100.dp,
                    height = Dp.Infinity
                ),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(
                        Base64.decode(
                            product.productImage[0],
                            Base64.DEFAULT
                        )
                    )
                    .crossfade(500)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 8.dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = product.productName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "Technical Specification",
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,
                    textDecoration = TextDecoration.Underline
                )

                Text(
                    text = "• ${productsFeatures[0]}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "• ${productsFeatures[1]}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 8.dp, bottom = 8.dp),
                    onClick = {
                        if (guestUser) {
                            onClick()
                        } else {
                            isDialogBox = true
                        }
                    }
                ) {
                    Icon(imageVector = Icons.Default.Call, contentDescription = null)
                    Text(text = "Call Now")
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

