package com.bottmac.bottmac.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun OrderScreen(
    modifier: Modifier,
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "No Order Placed Yet.")
    }

}

//
//@Composable
//fun OrderPlacedCard(
//    product: ProductItem,
//    onClick: () -> Unit
//) {
//    val productsFeatures = product.productFeatures.split("\\n")
//    var isDialogBox by rememberSaveable {
//        mutableStateOf(false)
//    }
//
//    AnimatedVisibility(visible = isDialogBox) {
//        ProductDialogBox { isDialogBox = false }
//    }
//
//    ElevatedCard(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(220.dp)
//            .padding(vertical = 8.dp, horizontal = 8.dp),
//        onClick = onClick
//    ) {
//        Row(
//            modifier = Modifier.fillMaxSize(),
//            verticalAlignment = Alignment.Top
//        ) {
//            AsyncImage(
//                modifier = Modifier.size(
//                    width = 100.dp,
//                    height = Dp.Infinity
//                ),
//                model = ImageRequest.Builder(LocalContext.current)
//                    .data(
//                        Base64.decode(
//                            product.productImage[0],
//                            Base64.DEFAULT
//                        )
//                    )
//                    .crossfade(500)
//                    .build(),
//                contentScale = ContentScale.Crop,
//                contentDescription = null
//            )
//            Spacer(modifier = Modifier.width(16.dp))
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(end = 8.dp),
//                verticalArrangement = Arrangement.SpaceAround
//            ) {
//                Text(
//                    text = product.productName,
//                    fontSize = 20.sp,
//                    fontWeight = FontWeight.Bold,
//                )
//                Text(
//                    text = "Technical Specification",
//                    fontSize = 16.sp,
//                    fontStyle = FontStyle.Italic,
//                    textDecoration = TextDecoration.Underline
//                )
//
//                Text(
//                    text = "• ${productsFeatures[0]}",
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis
//                )
//                Text(
//                    text = "• ${productsFeatures[1]}",
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis
//                )
//            }
//            Spacer(modifier = Modifier.width(16.dp))
//            Text(text = "Order Placed")
//        }
//        Spacer(modifier = Modifier.height(8.dp))
//    }
//}
//
