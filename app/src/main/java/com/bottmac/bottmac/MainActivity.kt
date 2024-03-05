package com.bottmac.bottmac

import android.os.Bundle
import android.util.Base64
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import java.lang.Byte


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var api: ProductsApi
    private lateinit var res: List<ProductItem>
//    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isComp = GlobalScope.launch {
            val response = api.getProducts()
            if (response.isSuccessful) {
                res = response.body()!!
                println(res)
                println(res[0].productFeatures.split("\\n")[0])
                println(res[0].productFeatures.split("\\n")[1])
//                println(res[1])
            }
//            println(1234567)
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
                    println(isComp)
//                        println(res[0].productImage[0])
                        println(21345678)
                        LazyColumn {
                            item { Text(text = res[0].productName) }
                            item { Text(text = res[0].productFeatures) }
                            item {
                                AsyncImage(
                                        modifier = Modifier.size(400.dp),
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(Base64.decode(res[0].productImage[0], Base64.DEFAULT))
                                            .crossfade(500)
                                            .build(),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        alignment = Alignment.Center
                                    )
                        }
//                        }
//                        LazyColumn {
//                            item {
//                                Text(text = res[1].productName, fontSize = 40.sp)
//                            }
//                            item {
//                                LazyRow {
//                                    items(res[1].productImage.size) { ind ->
//                                        AsyncImage(
//                                            modifier = Modifier.size(400.dp),
//                                            model = ImageRequest.Builder(LocalContext.current)
//                                                .data(
//                                                    Base64.decode(
//                                                        res[1].productImage[ind],
//                                                        Base64.DEFAULT
//                                                    )
//                                                )
//                                                .crossfade(500)
//                                                .build(),
//                                            contentDescription = null,
//                                            contentScale = ContentScale.Crop,
//                                            alignment = Alignment.Center
//                                        )
//                                    }
//                                }
//                            }
//                        }
                    }
                }
//                        LazyColumn {
//                            items(res.size) {ind->
//                                LazyRow {
//                                    item {
//                                        AsyncImage(
//                                            modifier = Modifier.size(400.dp),
//                                            model = ImageRequest.Builder(LocalContext.current)
//                                                .data(images[ind])
//                                                .crossfade(500)
//                                                .build(),
//                                            contentDescription = null,
//                                            contentScale = ContentScale.Crop,
//                                            alignment = Alignment.Center
//                                    })
//                                }
//                            }
//                        }
            }
        }
    }
}
}


