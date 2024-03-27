package com.bottmac.bottmac.presentation.product_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    mainNavController: NavController,
    primaryNavHostController: NavController
) {
    if (products.isNotEmpty()) {
        if (products.size == 1 && products[0].productId == -1) {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Error Loading Product's Data")
                Text(text = "Please check your internet connection")
                ElevatedButton(onClick = {
                    mainNavController.navigate(NavigationRoutes.Home.route) {
                        popUpTo(NavigationRoutes.Home.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }) {
                    Text(text = "Retry")
                }
            }
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
                            primaryNavHostController.navigate(NavigationRoutes.SignIn.route) {
                                popUpTo("mainScreen") {
                                    saveState = true
                                    inclusive = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        } else {
                            mainNavController.navigate("${NavigationRoutes.Home.route}/${productInd}/")
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
