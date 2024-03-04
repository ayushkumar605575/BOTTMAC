package com.bottmac.bottmac

import android.os.Bundle
import android.util.Base64
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.request.ImageRequest
import com.bottmac.bottmac.api.ProductsApi
import com.bottmac.bottmac.ui.theme.BOTTMACTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import coil.compose.AsyncImage
import com.bottmac.bottmac.models.ProductItem
import kotlinx.coroutines.DelicateCoroutinesApi

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var api: ProductsApi

    private var isComp = false
    private var res: List<ProductItem> = listOf()
//    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlobalScope.launch {
            val response = api.getProducts()
            if (response.isSuccessful) {
                res = response.body()!!
                println(res.size)
//                println(res[1])
                isComp = !isComp
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
//                    println(isComp.isCompleted)
                    if (isComp) {
                    println(isComp)
//                        println(res[1].productImage[0])
                        println(21345678)
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


