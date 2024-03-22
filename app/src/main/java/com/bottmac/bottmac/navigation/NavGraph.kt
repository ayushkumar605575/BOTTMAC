package com.bottmac.bottmac.navigation

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navigation
import com.bottmac.bottmac.email_sign_in_service.SignedInUser
import com.bottmac.bottmac.google_sign_in_service.GoogleAuthUiClient
import com.bottmac.bottmac.google_sign_in_service.SignInViewModel
import com.bottmac.bottmac.presentation.home_screen.MainScreenStructure
import com.bottmac.bottmac.presentation.product_search.HomeScreen
import com.bottmac.bottmac.presentation.profile.EditProfileScreen
import com.bottmac.bottmac.presentation.profile.OrderScreen
import com.bottmac.bottmac.presentation.profile.ProfileOptionNavigationStructure
import com.bottmac.bottmac.presentation.profile.ProfileOptions
import com.bottmac.bottmac.presentation.profile.ProfileScreen
import com.bottmac.bottmac.presentation.profile.ShippingAddressScreen
import com.bottmac.bottmac.presentation.sign_in_sign_up_screen.LoginScreen
import com.bottmac.bottmac.presentation.sign_in_sign_up_screen.SignUpScreen
import com.bottmac.bottmac.product_view_model.ProductsViewModel
import kotlinx.coroutines.launch

@Composable
fun NavGraph(
    navController: NavHostController,
    googleAuthUiClient: GoogleAuthUiClient
) {
    NavHost(navController = navController, startDestination = "startUp") {
        navigation(startDestination = NavigationRoutes.SignIn.route, route = "startUp") {
            composable(route = NavigationRoutes.SignIn.route) {
                val context = LocalContext.current
                val viewModel = it.sharedViewModel<SignInViewModel>(navController)
                val scope = rememberCoroutineScope()
                val state by viewModel.state.collectAsStateWithLifecycle()
                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartIntentSenderForResult()
                ) { result ->
                    if (result.resultCode == ComponentActivity.RESULT_OK) {
                        scope.launch {
                            val signInResult = googleAuthUiClient.signInWithIntent(
                                intent = result.data ?: return@launch
                            )
                            viewModel.onSignInResult(signInResult)
                        }
                    }
                }
                LaunchedEffect(key1 = state.isSignInSuccessful) {
                    if (state.isSignInSuccessful) {
                        Toast.makeText(
                            context,
                            "User Successfully Logged In",
                            Toast.LENGTH_LONG
                        ).show()
                        viewModel.resetState()
                        navController.navigate(NavigationRoutes.Home.route) {
                            popUpTo("startUp") {
                                inclusive = true
                            }
                        }
                    }
                }
                LoginScreen(
                    state = state,
                    onSignInClick = {
                        scope.launch {
                            val signInIntentSender = googleAuthUiClient.signIn()
                            launcher.launch(
                                IntentSenderRequest.Builder(
                                    signInIntentSender ?: return@launch
                                ).build()
                            )
                        }
                    },
                    navController = navController
                )
            }
            composable(route = NavigationRoutes.SignUp.route) {
                val scope = rememberCoroutineScope()
                val viewModel = it.sharedViewModel<SignInViewModel>(navController)
                val state by viewModel.state.collectAsStateWithLifecycle()
                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartIntentSenderForResult()
                ) { result ->
                    if (result.resultCode == ComponentActivity.RESULT_OK) {
                        scope.launch {
                            val signInResult = googleAuthUiClient.signInWithIntent(
                                intent = result.data ?: return@launch
                            )
                            viewModel.onSignInResult(signInResult)
                        }
                    }
                }
                SignUpScreen(
                    state = state,
                    onSignInClick = {
                        scope.launch {
                            val signInIntentSender = googleAuthUiClient.signIn()
                            launcher.launch(
                                IntentSenderRequest.Builder(
                                    signInIntentSender ?: return@launch
                                ).build()
                            )
                        }
                    },
                    navController = navController
                )
            }
        }
        navigation(startDestination = NavigationRoutes.Home.route, route = "main") {
            var screen: @Composable (PaddingValues) -> Unit
            composable(NavigationRoutes.Home.route) {
                val cSignedInUser: SignedInUser = hiltViewModel()
                val userData = cSignedInUser.signedInUserData.collectAsState().value
                val productsViewModel: ProductsViewModel = hiltViewModel()
                val products = productsViewModel.productItems.collectAsState().value
                var isSearchActive by rememberSaveable {
                    mutableStateOf(false)
                }
                screen = {
                    HomeScreen(
                        modifier = Modifier.padding(it),
                        products = products,
                        userData = userData,
                        isSearchActive = isSearchActive,
                        navController = navController
                    )
                }
                MainScreenStructure(
                    navController = navController,
                    onSearchActive = {
                        isSearchActive = !isSearchActive
                    },
                    screen = screen
                )
            }
            composable(NavigationRoutes.Profile.route) {
                val cSignedInUser: SignedInUser = hiltViewModel()
                val userData = cSignedInUser.signedInUserData.collectAsState().value
                screen = {
                    ProfileScreen(
                        modifier = Modifier.padding(it),
                        userData = userData,
                        cSignedInUser = cSignedInUser,
                        navController = navController
                    )
                }
                MainScreenStructure(
                    navController = navController,
                    onSearchActive = {},
                    screen = screen
                )
            }
        }
        navigation(startDestination = ProfileOptions.MyOrders.routes, route = "profileOptions") {
            var profileOptionScreen: @Composable (PaddingValues) -> Unit
            composable(ProfileOptions.MyOrders.routes) {
                profileOptionScreen = {
                    OrderScreen(modifier = Modifier.padding(it))
                }
                ProfileOptionNavigationStructure(
                    navController = navController,
                    title = ProfileOptions.MyOrders.title,
                    screen = profileOptionScreen
                )
            }
            composable(ProfileOptions.ShippingAddress.routes) {
                profileOptionScreen = {
                    ShippingAddressScreen(modifier = Modifier.padding(it))
                }
                ProfileOptionNavigationStructure(
                    navController = navController,
                    title = ProfileOptions.ShippingAddress.title,
                    screen = profileOptionScreen
                )
            }
            composable(ProfileOptions.EditProfile.routes) {
                profileOptionScreen = {
                    EditProfileScreen(modifier = Modifier.padding(it))
                }
                ProfileOptionNavigationStructure(
                    navController = navController,
                    title = ProfileOptions.EditProfile.title,
                    screen = profileOptionScreen
                )
            }
        }
    }
}


@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavHostController
): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(key1 = this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}
//
//@Composable
//fun NavGraph(
//    navController: NavHostController,
//    paddingValues: PaddingValues,
//    userType: (Int) -> Unit,
//    isSearchActive: Boolean,
//) {
//
//    NavHost(
//        navController = navController,
//        startDestination = NavigationRoutes.Home.route
//    ) {
////        navigation(startDestination = "startUp_Screen",route = "startUp")
//        composable(route = NavigationRoutes.Home.route) {
//            val cSignedInUser: SignedInUser = hiltViewModel()
//            val userData = cSignedInUser.signedInUserData.collectAsState().value
//            val productsViewModel: ProductsViewModel = hiltViewModel()
//            val products = productsViewModel.productItems.collectAsState().value
//            HomeScreen(
//                modifier = Modifier.padding(paddingValues),
//                products = products,
//                userData = userData,
//                userType = userType,
//                isSearchActive = isSearchActive
//            )
//        }
//        composable(route = NavigationRoutes.Cart.route) {
//            CartScreen(modifier = Modifier.padding(paddingValues), navController)
//        }
//        composable(route = NavigationRoutes.Order.route) {
//            OrderScreen(modifier = Modifier.padding(paddingValues), navController)
//        }
//        composable(route = NavigationRoutes.Fav.route) {
//            FavScreen(modifier = Modifier.padding(paddingValues))
//        }
//        composable(route = NavigationRoutes.Profile.route) {
//            val cSignedInUser: SignedInUser = hiltViewModel()
//            val userData = cSignedInUser.signedInUserData.collectAsState().value
//            ProfileScreen(
//                modifier = Modifier.padding(paddingValues),
//                userData = userData,
//                cSignedInUser = cSignedInUser,
//                navController = navController
//            )
//        }
//
//        composable(ProfileOptions.MyOrders.routes) {
////            val navArgument = it.arguments
//            OrderScreen(modifier = Modifier.padding(paddingValues), navController = navController)
//        }
//        composable(ProfileOptions.ShippingAddress.routes) {
////            val navArgument = it.arguments
//            ShippingAddressScreen(
//                modifier = Modifier.padding(paddingValues),
//                navController = navController
//            )
//        }
//        composable(ProfileOptions.EditProfile.routes) {
////            val navArgument = it.arguments
//            EditProfileScreen(
//                modifier = Modifier.padding(paddingValues),
//                navController = navController
//            )
//        }
//    }
//}
//

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        NavigationRoutes.Home,
        NavigationRoutes.Profile,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: NavigationRoutes,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    NavigationBarItem(
        selected = currentDestination?.route == screen.route,
        alwaysShowLabel = currentDestination?.route == screen.route,
        label = {
            Text(
                screen.title,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        },
        onClick = {
            println(navController.graph.findStartDestination().id)
            println(screen.route)
            navController.navigate(screen.route) {
                popUpTo("main") {
                    inclusive = false
                }
//                popUpTo(navController.graph.findStartDestination().id)
//                launchSingleTop = true
            }
        },
        icon = {
            Icon(
                imageVector = if (currentDestination?.route == screen.route) screen.selectedIcon else screen.unselectedIcon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    )
}
