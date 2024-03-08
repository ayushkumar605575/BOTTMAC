package com.bottmac.bottmac

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bottmac.bottmac.api.ProductsApi
import com.bottmac.bottmac.ui.theme.BOTTMACTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.bottmac.bottmac.models.ProductItem
import com.bottmac.bottmac.screens.LoginPage
import com.bottmac.bottmac.screens.ProductCard
import kotlinx.coroutines.DelicateCoroutinesApi


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var api: ProductsApi
    private lateinit var res: List<ProductItem>

//    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val isComp = GlobalScope.launch {
//            val response = api.getProducts()
//            if (response.isSuccessful) {
//                res = response.body()!!
//            }
//        }
        setContent {
            BOTTMACTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginPage()
//                    println(isComp.isCompleted)
//                    if (isComp.isCompleted) {
//                        LazyColumn {
//                            items(res) { productItem ->
//                                ProductCard(
//                                    productName = productItem.productName,
//                                    productsFeatures = productItem.productFeatures.split("\\n"),
//                                    productsImages = productItem.productImage
//                                )
//                                Spacer(modifier = Modifier.height(8.dp))
//                            }
//                        }
//                    }
                }
            }
        }
    }

}
