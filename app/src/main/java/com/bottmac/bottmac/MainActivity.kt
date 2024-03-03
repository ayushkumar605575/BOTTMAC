package com.bottmac.bottmac

import android.os.Bundle
import android.util.Base64
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.request.ImageRequest
import com.bottmac.bottmac.api.ProductsApi
import com.bottmac.bottmac.ui.theme.BOTTMACTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates
import coil.compose.AsyncImage

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var Api: ProductsApi
    var images by Delegates.notNull<ByteArray>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isComp = GlobalScope.launch {
            val response = Api.getProducts()
            if (response.isSuccessful) {
                val image = response.body()?.get(0)?.image
                images = Base64.decode(image, Base64.DEFAULT)

            }
            println(response)
        }
        setContent {
            BOTTMACTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (isComp.isCompleted) {
                        Column {
                            AsyncImage(
                                modifier = Modifier
                                    .fillMaxSize(),
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(images)
                                    .crossfade(500)
                                    .build(),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                alignment = Alignment.Center
                            )
                        }
                    }
                }
            }
        }
    }
}
