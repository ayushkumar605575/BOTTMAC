package com.bottmac.bottmac.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bottmac.bottmac.presentation.profile.ProfileScreen
import com.bottmac.bottmac.presentation.google_sign_in.GoogleAuthUiClient
import com.bottmac.bottmac.presentation.google_sign_in.SignedInState
import kotlinx.coroutines.launch

@Composable
fun NavGraph(
    state: SignedInState,
    onSignInClick: () -> Unit,
    navController: NavHostController,
    paddingValues: PaddingValues,
    lifecycleScope: LifecycleCoroutineScope,
    googleAuthUiClient: GoogleAuthUiClient,
    context: Context,
    isSigned: () -> Unit,
    isGuest: () -> Unit
) {
//    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.Home.route
    ) {

//        composable(route = NavigationRoutes.SignIn.route) {
//            LoginPage(state = state, onSignInClick = onSignInClick, navController = navController, isGuest = isGuest)
//        }
//
//        composable(route = NavigationRoutes.SignUp.route) {
//            SignUpPage(state = state, onSignInClick = onSignInClick, navController = navController, isGuest = isGuest)
//        }
        
//    NavHost(navController = navController, startDestination = NavigationRoutes.SignIn.route) {
        composable(route = NavigationRoutes.Home.route) {
            HomeScreen(modifier = Modifier.padding(paddingValues))
        }
        composable(route = NavigationRoutes.Cart.route) {
            CartScreen(modifier = Modifier.padding(paddingValues), navController)
        }
        composable(route = NavigationRoutes.Order.route) {
            OrderScreen(modifier = Modifier.padding(paddingValues), navController)
        }
        composable(route = NavigationRoutes.Fav.route) {
            FavScreen(modifier = Modifier.padding(paddingValues))
        }
        composable(route = NavigationRoutes.Search.route) {
            SearchScreen(Modifier.padding(paddingValues))
        }
        composable(route = NavigationRoutes.Profile.route) {
            ProfileScreen(
                userData = googleAuthUiClient.getSignedInUser(),
                onSignOut = {
                            lifecycleScope.launch { 
                                googleAuthUiClient.signOut()
                                Toast.makeText(
                                    context,
                                    "User Signed Out",
                                    Toast.LENGTH_LONG
                                ).show()
                                isSigned()
                            }
                },
                modifier = Modifier.padding(paddingValues)
            )
        }
//        composable(route = NavigationRoutes.SignUp.route) {
//            SignUpPage(paddingValues = paddingValues)
//        }
    }
}



@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        NavigationRoutes.Home,
        NavigationRoutes.Cart,
        NavigationRoutes.Profile
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
        label = { Text(screen.title, color = MaterialTheme.colorScheme.primary) },
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    )
}
