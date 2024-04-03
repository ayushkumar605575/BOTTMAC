package com.bottmac.bottmac.navigation

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bottmac.bottmac.email_sign_in_service.SignedInUser
import com.bottmac.bottmac.google_sign_in_service.GoogleAuthUiClient
import com.bottmac.bottmac.google_sign_in_service.SignInViewModel
import com.bottmac.bottmac.presentation.home_screen.MainScreenStructure
import com.bottmac.bottmac.presentation.profile.EditProfileScreen
import com.bottmac.bottmac.presentation.profile.OrderScreen
import com.bottmac.bottmac.presentation.profile.ProfileOptionNavigationStructure
import com.bottmac.bottmac.presentation.profile.ProfileOptions
import com.bottmac.bottmac.presentation.profile.ShippingAddressScreen
import com.bottmac.bottmac.presentation.sign_in_sign_up_screen.LoginScreen
import com.bottmac.bottmac.presentation.sign_in_sign_up_screen.SignUpScreen
import kotlinx.coroutines.launch

@Composable
fun NavGraph(
    navController: NavHostController,
    googleAuthUiClient: GoogleAuthUiClient
) {
    val cSignedInUser = hiltViewModel<SignedInUser>()

    NavHost(navController = navController, startDestination = "startUp") {
        navigation(startDestination = "loadingScreen", route = "startUp") {
            composable(route = "loadingScreen") {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) { CircularProgressIndicator() }
            }
            composable(
                route = NavigationRoutes.SignIn.route,
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
                        navController.navigate("mainScreen") {
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
            composable(
                route = NavigationRoutes.SignUp.route,
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
        navigation(startDestination = "mainScreen", route = "main") {
            composable(route = "mainScreen",
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
                },
                popExitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(durationMillis = 200, easing = LinearEasing)
                    )
                }
            ) {
                MainScreenStructure(
                    navController = navController,
                    cSignedInUser = cSignedInUser
                )
            }
        }
        navigation(startDestination = ProfileOptions.MyOrders.routes, route = "profileOptions") {
            var profileOptionScreen: @Composable (PaddingValues) -> Unit
            composable(route = ProfileOptions.MyOrders.routes,
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
                },
                popExitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(durationMillis = 200, easing = LinearEasing)
                    )
                }
            ) {
                profileOptionScreen = {
                    OrderScreen(modifier = Modifier
                        .fillMaxSize()
                        .padding(it))
                }
                ProfileOptionNavigationStructure(
                    navController = navController,
                    title = ProfileOptions.MyOrders.title,
                    screen = profileOptionScreen
                )
            }
            composable(route = ProfileOptions.ShippingAddress.routes,
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
                },
                popExitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(durationMillis = 200, easing = LinearEasing)
                    )
                }
            ) {
                profileOptionScreen = {
                    ShippingAddressScreen(modifier = Modifier.padding(it))
                }
                ProfileOptionNavigationStructure(
                    navController = navController,
                    title = ProfileOptions.ShippingAddress.title,
                    screen = profileOptionScreen
                )
            }
            composable(route = ProfileOptions.EditProfile.routes,
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
                },
                popExitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(durationMillis = 200, easing = LinearEasing)
                    )
                }
            ) { //backStackEntry ->
//                val cSignedInUser =
//                    backStackEntry.sharedViewModel<SignedInUser>(navController = navController)
                val userData by cSignedInUser.signedInUserData.collectAsStateWithLifecycle()

                profileOptionScreen = { paddingValues ->
                    EditProfileScreen(
                        modifier = Modifier.padding(paddingValues),
                        userData = userData,
                        cSignedInUser = cSignedInUser
                    )
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
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()//viewModel()
    val parentEntry = remember(key1 = this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}

@Composable
fun BottomBar(navController: NavHostController) {
    val bottomNavigationRoutes = listOf(
        NavigationRoutes.Home,
        NavigationRoutes.Profile,
    )
    var selectedScreenInd by rememberSaveable {
        mutableIntStateOf(0)
    }
    NavigationBar {
        bottomNavigationRoutes.forEachIndexed { index, bottomNavigationRoute ->
            AddItem(
                index = index,
                selectedScreenInd = selectedScreenInd,
                onSelection = { selectedScreenInd = index },
                bottomNavigationRoute = bottomNavigationRoute,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    index: Int,
    selectedScreenInd: Int,
    onSelection: () -> Unit,
    bottomNavigationRoute: NavigationRoutes,
    navController: NavHostController,
) {
    NavigationBarItem(
        selected = selectedScreenInd == index,
        alwaysShowLabel = selectedScreenInd == index,
        label = {
            Text(
                bottomNavigationRoute.title,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        },
        onClick = {
            navController.navigate(bottomNavigationRoute.route) {
                popUpTo("mainScreen") {
                    saveState = true
                    inclusive = true
                }
                restoreState = true
                launchSingleTop = true
            }
            onSelection()
        },
        icon = {
            Icon(
                imageVector = if (selectedScreenInd == index) bottomNavigationRoute.selectedIcon else bottomNavigationRoute.unselectedIcon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        },
        colors = NavigationBarItemColors(
            selectedIconColor = MaterialTheme.colorScheme.primary,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            selectedIndicatorColor = MaterialTheme.colorScheme.primaryContainer,
            unselectedIconColor = MaterialTheme.colorScheme.surface,
            unselectedTextColor = MaterialTheme.colorScheme.surface,
            disabledIconColor = MaterialTheme.colorScheme.onSurface,
            disabledTextColor = MaterialTheme.colorScheme.onSurface
        )
    )
}
