package com.bottmac.bottmac.presentation.product_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.bottmac.bottmac.google_sign_in_service.UserData
import com.bottmac.bottmac.models.ProductItem
import com.bottmac.bottmac.navigation.NavigationRoutes

@Composable
fun ProductScreen(
    modifier: Modifier,
    products: List<ProductItem>,
    userData: UserData,
    productDetails: Boolean,
    onProductDetails: () -> Unit,
    navController: NavController
) {

    var productDetailInd by rememberSaveable {
        mutableIntStateOf(-1)
    }

    if (products.isNotEmpty()) {
        if (productDetails) {
            ProductDetailsScreen(
                modifier = modifier,
                products[productDetailInd].productName,
                products[productDetailInd].productFeatures.split("\\n"),
                products[productDetailInd].productImage,
                onBack = onProductDetails//{ onProductDetails = !productDetails },
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(products.size) { productInd ->
                    ProductCard(
                        productName = products[productInd].productName,
                        productsFeatures = products[productInd].productFeatures.split("\\n"),
                        productsImages = products[productInd].productImage
                    ) {
                        if (userData.userId == null) {
                            navController.navigate(NavigationRoutes.SignIn.route) {
                                popUpTo("main") {
                                    inclusive = true
                                }
                            }
                        } else {
                            productDetailInd = productInd
                            onProductDetails()
                        }
                    }
                }
            }

        }
    } else {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) { CircularProgressIndicator() }
    }
}
