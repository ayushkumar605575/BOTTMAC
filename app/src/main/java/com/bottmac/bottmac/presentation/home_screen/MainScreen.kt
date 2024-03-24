package com.bottmac.bottmac.presentation.home_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.mediumTopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bottmac.bottmac.R
import com.bottmac.bottmac.email_sign_in_service.SignedInUser
import com.bottmac.bottmac.navigation.BottomBar
import com.bottmac.bottmac.navigation.NavigationRoutes
import com.bottmac.bottmac.presentation.product_details.ProductDetailsScreen
import com.bottmac.bottmac.presentation.product_search.HomeScreen
import com.bottmac.bottmac.presentation.profile.ProfileScreen
import com.bottmac.bottmac.product_view_model.ProductsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenStructure(
    navController: NavHostController,
) {
    val mainScreenNavController = rememberNavController()

    var isActiveSearch by rememberSaveable {
        mutableStateOf(false)
    }
    var currentRoute by rememberSaveable {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 12.dp, top = 8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp),
                        )
                        Text(
                            text = stringResource(id = R.string.app_name),
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 28.sp,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                },
                colors = mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary

                ),
                navigationIcon = {

                },
                actions = {
                    if (currentRoute != NavigationRoutes.Profile.route) {
                        IconButton(onClick = {
                            isActiveSearch = !isActiveSearch
                            mainScreenNavController.navigate(NavigationRoutes.Home.route) {
                                popUpTo(NavigationRoutes.Home.route) {
                                    inclusive = false
                                }
                                launchSingleTop = true
                            }
                        }) {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = "Search",
                            )
                        }
                    }
                }
            )
        },
        bottomBar = { BottomBar(navController = mainScreenNavController) },
    ) { paddingValues ->
        val cSignedInUser = hiltViewModel<SignedInUser>()
        val userData = cSignedInUser.signedInUserData.collectAsState().value
        val productsViewModel = hiltViewModel<ProductsViewModel>()
        val products = productsViewModel.productItems.collectAsState().value
        NavHost(
            navController = mainScreenNavController,
            startDestination = NavigationRoutes.Home.route,
        ) {
            composable(route = NavigationRoutes.Home.route) {
                currentRoute = mainScreenNavController.currentDestination?.route.toString()
                HomeScreen(
                    modifier = Modifier.padding(paddingValues),
                    products = products,
                    userData = userData,
                    isSearchActive = isActiveSearch,
                    mainNavController = mainScreenNavController,
                    primaryNavHostController = navController
                )
            }

            composable(
                route = "${NavigationRoutes.Home.route}/{productInd}/",
                arguments = listOf(navArgument("productInd") { NavType.StringType })
            ) { backstackEntry ->
                val productInd = backstackEntry.arguments?.getString("productInd")?.toInt() ?: "".toInt()
                println(productInd)
                ProductDetailsScreen(
                    modifier = Modifier.padding(paddingValues),
                    productName = products[productInd].productName,
                    productsFeatures = products[productInd].productFeatures.split("\\n"),
                    productsImages = products[productInd].productImage
                ) {

                }
            }

            composable(route = NavigationRoutes.Profile.route) {
                currentRoute = mainScreenNavController.currentDestination?.route.toString()
                ProfileScreen(
                    modifier = Modifier.padding(paddingValues),
                    userData = userData,
                    cSignedInUser = cSignedInUser,
                    primaryNavHostController = navController
                )
            }
        }
    }
}

