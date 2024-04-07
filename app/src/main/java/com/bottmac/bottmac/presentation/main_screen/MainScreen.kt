package com.bottmac.bottmac.presentation.main_screen

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.mediumTopAppBarColors
import androidx.compose.runtime.Composable
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import com.bottmac.bottmac.presentation.main_screen.home.HomeScreen
import com.bottmac.bottmac.presentation.main_screen.profile.ProfileScreen
import com.bottmac.bottmac.presentation.product_details.ProductDetailsScreen
import com.bottmac.bottmac.product_view_model.ProductsViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenStructure(
    navController: NavHostController,
    cSignedInUser: SignedInUser,
    productsViewModel: ProductsViewModel,
) {
//    cSignedInUser.getUserUpdatedData()
    val userData by cSignedInUser.signedInUserData.collectAsStateWithLifecycle()
    val products by productsViewModel.productItems.collectAsStateWithLifecycle()

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
                            painter = painterResource(id = R.drawable.logo_png),
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
                    AnimatedVisibility(
                        visible = currentRoute == "${NavigationRoutes.Home.route}/{productInd}/"
                    ) {
                        IconButton(onClick = {
                            mainScreenNavController.popBackStack()
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                },
                actions = {
                    AnimatedVisibility(
                        visible = currentRoute != NavigationRoutes.Profile.route
                    ) {
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
        // TODO: Check a way if possible to make these line of code execute 'n' number of times if internet is unavailable

//        val products = productsViewModel.productItems.collectAsState().value

        NavHost(
            navController = mainScreenNavController,
            startDestination = NavigationRoutes.Home.route,
        ) {
            composable(
                route = NavigationRoutes.Home.route,
                enterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(durationMillis = 200, easing = LinearEasing),
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(durationMillis = 200, easing = LinearEasing)
                    )
                }
            ) {

                currentRoute = mainScreenNavController.currentDestination?.route.toString()
                HomeScreen(
                    modifier = Modifier.padding(paddingValues),
                    products = products,
                    userData = userData,
                    isSearchActive = isActiveSearch,
                    mainNavController = mainScreenNavController,
                    primaryNavHostController = navController,
                    onRetry = { productsViewModel.getLatestProduct() }
                )
            }

            composable(
                route = "${NavigationRoutes.Home.route}/{productInd}/",
                arguments = listOf(navArgument("productInd") { NavType.StringType }),
                enterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(durationMillis = 200, easing = LinearEasing),
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(durationMillis = 200, easing = LinearEasing)
                    )
                }
            ) { backstackEntry ->
//                val productsViewModel = hiltViewModel<ProductsViewModel>()
//                val products = productsViewModel.productItems.collectAsStateWithLifecycle().value
                val productId =
                    backstackEntry.arguments?.getString("productInd")?.toInt() ?: -1
                val productItemDetail = products.first {
                    it.productId == productId
                }
                if (products.isNotEmpty()) {
                    ProductDetailsScreen(
                        modifier = Modifier.padding(paddingValues),
                        productName = productItemDetail.productName,
                        productsFeatures = productItemDetail.productFeatures.split("\\n"),
                        productsImages = productItemDetail.productImage
                    ) {
                        mainScreenNavController.popBackStack()
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) { CircularProgressIndicator() }
                }
            }

            composable(
                route = NavigationRoutes.Profile.route,
                enterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(durationMillis = 200, easing = LinearEasing),
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(durationMillis = 200, easing = LinearEasing)
                    )
                }
            ) {
//                val userData = cSignedInUser.signedInUserData.collectAsStateWithLifecycle()
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

fun displayToast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
}