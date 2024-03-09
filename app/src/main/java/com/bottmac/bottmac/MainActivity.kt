package com.bottmac.bottmac

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.bottmac.bottmac.api.ProductsApi
import com.bottmac.bottmac.screens.MainScreen
import com.bottmac.bottmac.ui.theme.BOTTMACTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var api: ProductsApi
//    private lateinit var res: List<ProductItem>

//    @OptIn(DelicateCoroutinesApi::class)
//    @OptIn(ExperimentalMaterial3Api::class)
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
                    MainScreen()
                }
            }
        }
    }

}
