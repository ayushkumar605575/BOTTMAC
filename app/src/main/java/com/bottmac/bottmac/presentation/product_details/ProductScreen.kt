package com.bottmac.bottmac.presentation.product_details

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
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
                                product = products[productInd],
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
                                    mainNavController.navigate("${NavigationRoutes.Home.route}/${productInd}/")
                                }
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
}

