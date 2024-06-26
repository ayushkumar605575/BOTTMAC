package com.bottmac.bottmac.presentation.product_details

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bottmac.bottmac.models.ProductItem
import com.bottmac.bottmac.navigation.NavigationRoutes
import com.bottmac.bottmac.userdata.UserData

@Composable
fun ProductScreen(
    modifier: Modifier,
    products: List<ProductItem>,
    isSearchActive: Boolean,
    userData: UserData,
    mainNavController: NavController,
    primaryNavHostController: NavController,
    onRetry: () -> Unit,
) {
    val guestUser = userData.userId == null
    var isDialogBox by rememberSaveable {
        mutableStateOf(false)
    }

    AnimatedVisibility(visible = isDialogBox) {
        ProductDialogBox { isDialogBox = false }
    }

    AnimatedContent(targetState = products.isNotEmpty(), label = "") { isProducts ->
        if (isProducts) {
            AnimatedContent(
                targetState = products.size == 1 && products[0].productId == -1,
                label = ""
            ) { hasError ->
                if (hasError) {
                    Column(
                        modifier = modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Error Loading Product's Data")
                        Text(text = "Please check your internet connection")
                        ElevatedButton(
                            onClick = onRetry
//                            onRetry()
//                            mainNavController.navigate(NavigationRoutes.Home.route) {
//                                popUpTo(NavigationRoutes.Home.route) {
//                                    inclusive = true
//                                }
//                                launchSingleTop = true
//                            }
                        ) {
                            Text(text = "Retry")
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(products) { product ->
                            ProductCard(
                                product = product,
                                guestUser = guestUser,
                            ) {
                                if (guestUser) {
                                    primaryNavHostController.navigate(NavigationRoutes.SignIn.route) {
                                        popUpTo("mainScreen") {
                                            saveState = true
                                            inclusive = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                } else {
                                    mainNavController.navigate("${NavigationRoutes.Home.route}/${product.productId}/")

                                }
                            }
                        }
                    }
                }
            }
        } else if (products.isEmpty() && isSearchActive) {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) { Text(text = "No Product Found", fontSize = 24.sp) }
        } else {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) { CircularProgressIndicator() }
        }
    }
}

