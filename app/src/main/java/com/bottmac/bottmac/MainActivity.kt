package com.bottmac.bottmac

import android.os.Bundle
import android.util.Base64
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bottmac.bottmac.api.ProductsApi
import com.bottmac.bottmac.ui.theme.BOTTMACTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.bottmac.bottmac.models.ProductItem
import kotlinx.coroutines.DelicateCoroutinesApi


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var api: ProductsApi
    private lateinit var res: List<ProductItem>

        @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isComp = GlobalScope.launch {
            val response = api.getProducts()
            if (response.isSuccessful) {
                res = response.body()!!
            }
        }
        setContent {
            BOTTMACTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    println(isComp.isCompleted)
                    if (isComp.isCompleted) {
//                        val productItem = res
                        LazyColumn {
                            items(res) { productItem ->
                                ProductCard(
                                    productName = productItem.productName,
                                    productsFeatures = productItem.productFeatures.split("\\n"),
                                    productsImages = productItem.productImage
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}
//}


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

@Preview(showBackground = true)
@Composable
private fun CardPrev() {
//    ProductCard(productsFeatures = listOf("ayush", "kumar", "Naveen"))
}